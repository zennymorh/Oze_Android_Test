package com.zennymorh.ozeandroidtest.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.zennymorh.ozeandroidtest.api.ApiService
import com.zennymorh.ozeandroidtest.data.model.UserDetail
import com.zennymorh.ozeandroidtest.data.model.UsersDB
import com.zennymorh.ozeandroidtest.db.UserDAO
import com.zennymorh.ozeandroidtest.paging.UserRemoteMediator
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

sealed class ListState {
    data class Success(val data: UserDetail?): ListState()
    data class Error(val message: String): ListState()
    object Loading: ListState()
}

class UserRepositoryImpl @Inject constructor(
    private val userDAO: UserDAO,
    private val userRemoteMediator: UserRemoteMediator,
    private val apiService: ApiService
): UserRepository {

    override fun getUserDetail(username: String): Disposable {
        return apiService.getUserDetail(username)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { detail ->
                    ListState.Loading
                    if (detail != null) {
                        ListState.Success(data = detail)
                    }

                },
                {
                    ListState.Error(message = it.message.toString())
                }
            )
    }

//    @OptIn(ExperimentalPagingApi::class)
//    override fun getUsersWithNetwork(): Disposable {
//        return Pager(
//            config = PagingConfig(
//                pageSize = 30,
//                enablePlaceholders = true,
//                maxSize = 40,
//                prefetchDistance = 5,
//                initialLoadSize = 40,
//
//                ),
//            remoteMediator = userRemoteMediator,
//            pagingSourceFactory = {userDAO.selectAll()}
//        )
//            .flowable
//            .subscribeOn(Schedulers.io())
//            .subscribe(
//                { usersDb ->
//                    loading.postValue(true)
//                    ListState.Loading
//
//                    if (usersDb != null) {
//                        error.postValue(false)
//                        data.postValue(usersDb)
//                        ListState.Success(data = usersDb)
//                        message.postValue("Successfully retrieved data")
//                    }
//                    loading.postValue(false)
//                    message.postValue(null)
//                },
//                {
//                    error.postValue(false)
//                    message.postValue("An error occurred")
//                    ListState.Error(message = "An error occurred")
//                }
//            )
//    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getUsers(): Flowable<PagingData<UsersDB.UserDb>> {

        return Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = true,
                maxSize = 40,
                prefetchDistance = 5,
                initialLoadSize = 40,

            ),
            remoteMediator = userRemoteMediator,
            pagingSourceFactory = {
                userDAO.selectAll()
            }
        ).flowable


    }

}