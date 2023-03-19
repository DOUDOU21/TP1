package com.ndoudou.tp1.data.local.entity

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
    val email: String,
    val nom: String,
    val prenom: String,
    val ville: String,
    val pays: String,
    val fonction: String,
    val description: String,
    val tel: String,
    val portable: String,
    val photo: String

)  {
    fun toUser(): User {
        return User(
            id = id,
            email = email,
            nom = nom,
            prenom = prenom,
            ville = ville,
            pays = pays,
            fonction = fonction,
            description = description,
            tel = tel,
            portable = portable,
            photo = photo
        )
    }
}