package com.zennymorh.ozeandroidtest.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.zennymorh.ozeandroidtest.api.ApiService
import com.zennymorh.ozeandroidtest.data.model.UsersDB
import com.zennymorh.ozeandroidtest.db.UserDAO
import com.zennymorh.ozeandroidtest.db.UserDatabase
import com.zennymorh.ozeandroidtest.db.UserRemoteKeyDAO
import com.zennymorh.ozeandroidtest.util.UserMapper
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.InvalidObjectException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator @Inject constructor(
    private val apiService: ApiService,
    private val userDatabase: UserDatabase,
    private val userDAO: UserDAO,
    private val userRemoteKeyDAO: UserRemoteKeyDAO,
    private val userMapper: UserMapper
): RxRemoteMediator<Int, UsersDB.UserDb>() {

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, UsersDB.UserDb>
    ): Single<MediatorResult> {

        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map {
                when (it) {
                    LoadType.REFRESH -> {
                        val keys = getKeyClosestToCurrentPosition(state)

                        keys?.nextKey?.minus(1) ?: 1
                    }
                    LoadType.APPEND -> {
                        val remoteKeys = getKeyForLastItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.nextKey ?: INVALID_PAGE
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getKeyForFirstItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.prevKey ?: INVALID_PAGE
                    }
                }
            }
            .flatMap { page ->
                if (page == INVALID_PAGE) {
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                }

                apiService.getUsers(
                    page = page,
                    q = "lagos"
                )
                    .map { users ->
                        userMapper.toUserDb(users)
                    }
                    .map { usersDb ->
                        insertIntoDb(page, loadType, usersDb)
                    }
                    .map<MediatorResult> { usersDb ->
                        MediatorResult.Success(endOfPaginationReached = page > usersDb.endOfPage)
                    }
                    .onErrorReturn { MediatorResult.Error(it) }
            }
            .onErrorReturn { MediatorResult.Error(it) }
    }

    private fun insertIntoDb(page: Int, loadType: LoadType, data: UsersDB): UsersDB {
        userDatabase.beginTransaction()

        try {
            if (loadType == LoadType.REFRESH) {
                userRemoteKeyDAO.deleteKeys()
                userDAO.deleteUsers()
            }

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (page > data.endOfPage) null else page + 1
            val keys = data.users.map {
                UsersDB.UserKeys(
                    id = it.id,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }

            userRemoteKeyDAO.insertAll(keys)
            userDAO.insertAll(data.users)
            userDatabase.setTransactionSuccessful()

        } finally {
            userDatabase.endTransaction()
        }

        return data
    }

    private fun getKeyForLastItem(state: PagingState<Int, UsersDB.UserDb>): UsersDB.UserKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { user ->
            userRemoteKeyDAO.selectKeyById(user.id)
        }
    }

    private fun getKeyForFirstItem(state: PagingState<Int, UsersDB.UserDb>): UsersDB.UserKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { user ->
            userRemoteKeyDAO.selectKeyById(user.id)
        }
    }

    private fun getKeyClosestToCurrentPosition(state: PagingState<Int, UsersDB.UserDb>): UsersDB.UserKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                userRemoteKeyDAO.selectKeyById(id)
            }
        }
    }

    companion object {
        const val INVALID_PAGE = -1
    }
}