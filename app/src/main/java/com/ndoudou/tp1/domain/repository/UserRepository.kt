package com.ndoudou.tp1.domain.repository

import androidx.lifecycle.LiveData
import com.ndoudou.tp1.data.local.entity.UserEntity
import com.ndoudou.tp1.domain.model.User

interface UserRepository {
    suspend fun insertUser(user: User): Long
    fun updateUser(user: User)
    fun deleteUser(user: User)
    fun getUserById(id: Long): LiveData<User?>?
    suspend fun getUsers(): List<User>
    suspend fun getPagedList(limit: Int, offset: Int): List<User>
}