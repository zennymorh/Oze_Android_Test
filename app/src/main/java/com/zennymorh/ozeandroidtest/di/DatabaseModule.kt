package com.zennymorh.ozeandroidtest.di

import android.content.Context
import androidx.room.Room
import com.zennymorh.ozeandroidtest.db.UserDAO
import com.zennymorh.ozeandroidtest.db.UserDatabase
import com.zennymorh.ozeandroidtest.db.UserRemoteKeyDAO
import com.zennymorh.ozeandroidtest.util.UserMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext context: Context): UserDatabase =
        Room.databaseBuilder(context, UserDatabase::class.java, "USER_DB")
            .build()

    @Provides
    @Singleton
    fun provideUserDao(userDatabase: UserDatabase): UserDAO =
        userDatabase.userDao()

    @Provides
    @Singleton
    fun provideUserRemoteKeyDao(userDatabase: UserDatabase): UserRemoteKeyDAO =
        userDatabase.userRemoteKeyDao()

    @Provides
    @Singleton
    fun provideMapper(): UserMapper =
        UserMapper()

}