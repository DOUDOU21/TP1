package com.ndoudou.tp1.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ndoudou.tp1.domain.model.UserRemoteKey
import com.ndoudou.tp1.utils.USER_REMOTE_KEYS

@Entity(tableName = USER_REMOTE_KEYS)
data class UserRemoteKeysEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "next_page") val nextPage: Int?,
    @ColumnInfo(name = "previous_page") val previousPage: Int?
){
    fun toUserRemoteKey(): UserRemoteKey{
        return UserRemoteKey(
            id,
            nextPage,
            previousPage
        )
    }
}