package com.ndoudou.tp1.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ndoudou.tp1.domain.model.User
import com.ndoudou.tp1.utils.USER_TABLE_NAME

@Entity(
    tableName = USER_TABLE_NAME
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    val email: String,
    val city: String,
    val country: String,
    val function: String,
    val description: String,
    val phone: String,
    val portable: String,
    val avatar: String

)  {
    constructor(id: Int, firstName: String, lastName: String, email: String, avatar: String) :
            this(id, firstName, lastName, email,"","","","","","", avatar)

    fun toUser(): User {
        return User(
            id = id,
            firstName = firstName,
            lastName = lastName,
            email = email,
            city = city,
            country = country,
            function = function,
            description = description,
            phone = phone,
            portable = portable,
            avatar = avatar
        )
    }
}