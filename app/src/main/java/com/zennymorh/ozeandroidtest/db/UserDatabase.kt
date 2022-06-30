package com.zennymorh.ozeandroidtest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zennymorh.ozeandroidtest.data.model.UsersDB

@Database(
    entities = [UsersDB.UserDb::class, UsersDB.UserKeys::class],
    exportSchema = false,
    version = 1
)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDAO
    abstract fun userRemoteKeyDao(): UserRemoteKeyDAO
}