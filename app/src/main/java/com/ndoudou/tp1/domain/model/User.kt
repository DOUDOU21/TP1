package com.ndoudou.tp1.domain.model


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
): java.io.Serializable