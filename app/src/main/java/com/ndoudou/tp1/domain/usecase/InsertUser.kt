package com.ndoudou.tp1.domain.usecase

import com.ndoudou.tp1.data.local.entity.UserEntity
import com.ndoudou.tp1.domain.model.User
import com.ndoudou.tp1.domain.repository.UserRepository

class InsertUser (private val userRepository: UserRepository) {
    suspend fun execute(user: User) = userRepository.insertUser(user)

}