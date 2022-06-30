package com.zennymorh.ozeandroidtest.api

import com.zennymorh.ozeandroidtest.data.model.UserDetail
import com.zennymorh.ozeandroidtest.data.model.Users
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getUsers(
        @Query("page") page: Int,
        @Query("q") q: String
    ): Single<Users>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Flowable<UserDetail>
}