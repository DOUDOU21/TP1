package com.ndoudou.tp1.domain.usecase

import com.ndoudou.tp1.domain.repository.UserRepository

class GetUsers (private val userRepository: UserRepository) {
    //suspend operator fun invoke() = userRepository.getUsers()
    suspend operator fun invoke(limit: Int, offset: Int) = userRepository.getPagedList(limit, offset)
}