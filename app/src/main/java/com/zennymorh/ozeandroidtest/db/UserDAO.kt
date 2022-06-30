package com.zennymorh.ozeandroidtest.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zennymorh.ozeandroidtest.data.model.UsersDB

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UsersDB.UserDb>)

    @Query("SELECT * FROM users ORDER BY id ASC")
    fun selectAll(): PagingSource<Int, UsersDB.UserDb>

    @Query("DELETE FROM users")
    fun deleteUsers()
}