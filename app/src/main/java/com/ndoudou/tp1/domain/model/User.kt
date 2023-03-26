package com.ndoudou.tp1.domain.model

import com.ndoudou.tp1.data.local.entity.UserEntity
import com.ndoudou.tp1.data.remote.dto.UserDto
import java.io.Serializable

data class User(
    val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val email: String,
    val city: String,
    val country: String,
    val function: String,
    val description: String,
    val phone: String,
    val portable: String,
    val avatar: String
): Serializable {

    constructor(id: Int, firstName: String, lastName: String, email: String, avatar: String) :
            this(id, firstName, lastName, email,"","","","","","", avatar)

    fun toUser(): UserEntity {
        return UserEntity(
            id = id,
            email = email,
            lastName = lastName,
            firstName = firstName,
            city = city,
            country = country,
            function = function,
            description = description,
            phone = phone,
            portable = portable,
            avatar = avatar
        )
    }

    fun toUserDto(): UserDto {
        return UserDto(
            id,
            firstName,
            lastName,
            email,
            avatar
        )
    }
}