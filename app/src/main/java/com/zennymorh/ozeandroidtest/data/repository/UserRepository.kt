package com.zennymorh.ozeandroidtest.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.zennymorh.ozeandroidtest.data.model.Users
import com.zennymorh.ozeandroidtest.data.model.UsersDB
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable

interface UserRepository {
    fun getUsers(): Flowable<PagingData<UsersDB.UserDb>>

    fun getUserDetail(username: String): Disposable
}