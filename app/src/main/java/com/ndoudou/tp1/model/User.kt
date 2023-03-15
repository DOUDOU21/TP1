package com.ndoudou.tp1.model

import java.io.Serializable

data class User(
    var nom: String?,
    var prenom: String?,
    var email: String?,
    var ville: String?,
    var pays: String?,
    var fonction: String?,
    var description: String?,
    var tel: String?,
    var portable: String?,
    var photo: String?
)  : Serializable