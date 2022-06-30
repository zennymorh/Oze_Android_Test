package com.zennymorh.ozeandroidtest.di

import com.zennymorh.ozeandroidtest.api.ApiService
import com.zennymorh.ozeandroidtest.db.UserDAO
import com.zennymorh.ozeandroidtest.db.UserDatabase
import com.zennymorh.ozeandroidtest.db.UserRemoteKeyDAO
import com.zennymorh.ozeandroidtest.paging.UserRemoteMediator
import com.zennymorh.ozeandroidtest.util.UserMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PagingModule {

    @Provides
    @Singleton
    fun provideRemoteMediator(
        apiService: ApiService,
        userDatabase: UserDatabase,
        userDAO: UserDAO,
        userRemoteKeyDAO: UserRemoteKeyDAO,
        userMapper: UserMapper
    ): UserRemoteMediator =
        UserRemoteMediator(
            apiService = apiService,
            userDatabase = userDatabase,
            userDAO = userDAO,
            userRemoteKeyDAO = userRemoteKeyDAO,
            userMapper = userMapper
        )
}