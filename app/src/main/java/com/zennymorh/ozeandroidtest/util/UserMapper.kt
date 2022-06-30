package com.zennymorh.ozeandroidtest.util

import com.zennymorh.ozeandroidtest.data.model.Users
import com.zennymorh.ozeandroidtest.data.model.UsersDB

class UserMapper {

    fun toUserDb(data: Users): UsersDB {
        return with(data) {
            UsersDB(
                totalCount = totalCount,
                incompleteResults = incompleteResults,
                users = users.map {
                    UsersDB.UserDb(
                        avatarUrl = it.avatarUrl,
                        eventsUrl = it.eventsUrl,
                        followersUrl = it.followersUrl,
                        followingUrl = it.followingUrl,
                        gistsUrl = it.gistsUrl,
                        gravatarId = it.gravatarId,
                        htmlUrl = it.htmlUrl,
                        id = it.id,
                        login = it.login,
                        nodeId = it.nodeId,
                        organizationsUrl = it.organizationsUrl,
                        receivedEventsUrl = it.receivedEventsUrl,
                        reposUrl = it.reposUrl,
                        score = it.score,
                        siteAdmin = it.siteAdmin,
                        starredUrl = it.starredUrl,
                        subscriptionsUrl = it.subscriptionsUrl,
                        type = it.type,
                        url = it.url
                    )
                }
            )
        }
    }
}