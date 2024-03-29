package com.ndoudou.tp1.domain.usecase

import com.ndoudou.tp1.domain.repository.UserRepository

class GetUser (private val userRepository: UserRepository) {
    operator fun invoke(id: Int) = userRepository.getUserById(id)

}