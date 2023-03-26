package com.ndoudou.tp1.domain.usecase

import com.ndoudou.tp1.domain.model.User
import com.ndoudou.tp1.domain.repository.UserRepository

class InsertUsers (private val userRepository: UserRepository) {

    suspend fun execute(users: List<User>) = userRepository.insertUsers(users)

}