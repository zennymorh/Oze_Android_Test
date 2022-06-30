package com.zennymorh.ozeandroidtest.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UsersDB(
    val incompleteResults: Boolean,
    val users: List<UserDb>,
    val totalCount: Int
): Parcelable {

    @IgnoredOnParcel
    val endOfPage = totalCount / 30

    @Parcelize
    @Entity(tableName = "users")
    data class UserDb(
        val avatarUrl: String,
        val eventsUrl: String,
        val followersUrl: String,
        val followingUrl: String,
        val gistsUrl: String,
        val gravatarId: String,
        val htmlUrl: String,
        @PrimaryKey
        val id: Int,
        val login: String,
        val nodeId: String,
        val organizationsUrl: String,
        val receivedEventsUrl: String,
        val reposUrl: String,
        val score: Double,
        val siteAdmin: Boolean,
        val starredUrl: String,
        val subscriptionsUrl: String,
        val type: String,
        val url: String
    ): Parcelable

    @Parcelize
    @Entity(tableName = "user_keys")
    data class UserKeys(
        @PrimaryKey
        val id: Int,
        val prevKey: Int?,
        val nextKey: Int?
    ): Parcelable
}