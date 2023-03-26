package com.ndoudou.tp1.domain.usecase

import com.ndoudou.tp1.domain.model.UserRemoteKey
import com.ndoudou.tp1.domain.repository.UserRepository

class InsertUserRemoteKeys (private val userRepository: UserRepository) {

    suspend fun execute(keys: List<UserRemoteKey>) = userRepository.insertUserRemoteKeys(keys)

}