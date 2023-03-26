package com.ndoudou.tp1.domain.usecase

import com.ndoudou.tp1.domain.repository.UserRepository

class GetUserRemoteKey (private val userRepository: UserRepository) {

    suspend operator fun invoke(id: Int) = userRepository.getUserRemoteKey(id)

}