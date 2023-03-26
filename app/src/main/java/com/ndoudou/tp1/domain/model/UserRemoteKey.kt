package com.ndoudou.tp1.domain.model

import com.ndoudou.tp1.data.local.entity.UserRemoteKeysEntity

data class UserRemoteKey(
    val id: Int,
    val nextPage: Int?,
    val previousPage: Int?
){
    fun toUserRemoteKey(): UserRemoteKeysEntity{
        return UserRemoteKeysEntity(
            id,
            nextPage,
            previousPage
        )
    }
}
