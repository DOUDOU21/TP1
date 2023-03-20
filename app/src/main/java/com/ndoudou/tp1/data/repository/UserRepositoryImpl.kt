package com.ndoudou.tp1.data.repository

import androidx.lifecycle.LiveData
import com.ndoudou.tp1.data.local.dao.UserDao
import com.ndoudou.tp1.data.local.database.DataBase
import com.ndoudou.tp1.data.local.entity.UserEntity
import com.ndoudou.tp1.domain.model.User
import com.ndoudou.tp1.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun insertUser(user: User): Long {
        return userDao.insertUser(user.toUser())
    }

    override suspend fun updateUser(user: User) {
        userDao.updateUser(user.toUser())
    }

    override suspend fun deleteUser(user: User) {
        userDao.deleteUser(user.toUser())
    }

    override fun getUserById(id: Int): User? {
        return userDao.getUserById(id)?.toUser()
    }

    override suspend fun getUsers(): List<User> {
        return userDao.getUsers().map { it.toUser() }
    }

    override suspend fun getPagedList(limit: Int, offset: Int): List<User> {
        return userDao.getPagedList(limit, offset).map { it.toUser() }
    }
}