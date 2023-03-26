package com.ndoudou.tp1.domain.usecase

import com.ndoudou.tp1.domain.repository.UserRepository

class ClearUserTable (private val userRepository: UserRepository) {

    suspend fun execute() = userRepository.clearUserTable()

}