package com.zennymorh.ozeandroidtest.ui.userslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.zennymorh.ozeandroidtest.data.model.UsersDB
import com.zennymorh.ozeandroidtest.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {

    fun getUsers(): Flowable<PagingData<UsersDB.UserDb>> {
        return repository.getUsers()
            .cachedIn(viewModelScope)
    }
}