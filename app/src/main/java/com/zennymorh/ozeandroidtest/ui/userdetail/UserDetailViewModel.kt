package com.zennymorh.ozeandroidtest.ui.userdetail

import androidx.lifecycle.ViewModel
import com.zennymorh.ozeandroidtest.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val repository: UserRepository,
    private val compositeDisposable: CompositeDisposable
): ViewModel() {

    fun getUserDetails(username: String) {
        compositeDisposable.add(repository.getUserDetail(username))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }}