package com.ndoudou.tp1.domain.model

import com.ndoudou.tp1.data.local.entity.UserEntity


data class User(
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
): java.io.Serializable {
    fun toUser(): UserEntity {
        return UserEntity(
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