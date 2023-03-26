package com.ndoudou.tp1.data.remote.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.ndoudou.tp1.data.local.entity.UserEntity
import com.ndoudou.tp1.domain.model.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDto(
    val id: Int,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val email: String,
    val avatar: String
) : Parcelable {
    fun toUserEntity(): UserEntity {
        return UserEntity(
            id,
            firstName,
            lastName,
            email,
            avatar
        )
    }

    fun toUser(): User {
        return User(
            id,
            firstName,
            lastName,
            email,
            avatar
        )
    }
}