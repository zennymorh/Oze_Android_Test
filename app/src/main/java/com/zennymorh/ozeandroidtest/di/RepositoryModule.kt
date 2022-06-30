package com.zennymorh.ozeandroidtest.di

import com.zennymorh.ozeandroidtest.api.ApiService
import com.zennymorh.ozeandroidtest.data.repository.UserRepository
import com.zennymorh.ozeandroidtest.data.repository.UserRepositoryImpl
import com.zennymorh.ozeandroidtest.db.UserDAO
import com.zennymorh.ozeandroidtest.paging.UserRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(
        userDAO: UserDAO,
        userRemoteMediator: UserRemoteMediator,
        apiService: ApiService
    ): UserRepository =
        UserRepositoryImpl(
            userDAO = userDAO,
            userRemoteMediator = userRemoteMediator,
            apiService = apiService
        )
}