package com.ndoudou.tp1.domain.usecase

import com.ndoudou.tp1.domain.repository.UserRepository

class GetUsersPage (private val userRepository: UserRepository) {

    suspend operator fun invoke(pageSize: Int) = userRepository.getUsersPage(pageSize)

}