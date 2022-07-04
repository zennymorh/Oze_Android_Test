package com.zennymorh.ozeandroidtest.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zennymorh.ozeandroidtest.data.model.UsersDB

@Dao
interface UserRemoteKeyDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(key: List<UsersDB.UserKeys?>?)

    @Query("SELECT * FROM user_keys WHERE id = :id")
    fun selectKeyById(id: Int): UsersDB.UserKeys

    @Query("DELETE FROM user_keys")
    fun deleteKeys()
}