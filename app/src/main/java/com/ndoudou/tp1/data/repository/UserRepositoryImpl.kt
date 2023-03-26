package com.ndoudou.tp1.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.*
import com.ndoudou.tp1.data.local.dao.UserDao
import com.ndoudou.tp1.data.local.database.DataBase
import com.ndoudou.tp1.data.local.entity.UserEntity
import com.ndoudou.tp1.data.remote.api.Api
import com.ndoudou.tp1.data.remote.dto.UserDto
import com.ndoudou.tp1.data.repository.paging.UserRemoteMediator
import com.ndoudou.tp1.domain.model.User
import com.ndoudou.tp1.domain.model.UserRemoteKey
import com.ndoudou.tp1.domain.repository.UserRepository
import com.ndoudou.tp1.utils.Resource
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val api: Api
) : UserRepository {

    override suspend fun insertUser(user: User): Long {
        return userDao.insertUser(user.toUser())
    }

    override suspend fun insertUsers(users: List<User>) {
        userDao.insertUsers(users = users.map { it.toUser() })
    }

    override suspend fun updateUser(user: User) {
        userDao.updateUser(user.toUser())
    }

    override suspend fun deleteUser(user: User) {
        userDao.deleteUser(user.toUser())
    }

    override fun getUserById(id: Int): Single<User>{
        return userDao.getUserById(id).map {
            it.toUser()
        }
    }

    override suspend fun getUsers(): List<User> {
        return userDao.getUsers().map { it.toUser() }
    }

    override suspend fun getUsers(page: Int): List<User> {
        return api.getUsers(page).data.map {
            it.toUser()
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getUsersPage(pageSize: Int): Flow<PagingData<User>> {
        val remoteMediator = UserRemoteMediator(
            api = api,
            userDao = userDao
        )
        return Pager(
            config = PagingConfig(pageSize = pageSize, prefetchDistance = 1, initialLoadSize = 5),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { userDao.getUsersPages() }
        ).flow.map {
            it.map { userEntity->
                userEntity.toUser()
            }
        }
    }

    override suspend fun getPagedList(limit: Int, offset: Int): List<User> {
        return userDao.getPagedList(limit, offset).map { it.toUser() }
    }

    override suspend fun getUserRemoteKey(id: Int): UserRemoteKey {
        return userDao.getUserRemoteKey(id).toUserRemoteKey()
    }

    override suspend fun insertUserRemoteKeys(keys: List<UserRemoteKey>) {
        userDao.insertUserRemoteKeys(keys.map { key-> key.toUserRemoteKey() })
    }

    override suspend fun clearUserRemoteKeysTable() {
        userDao.clearUserRemoteKeysTable()
    }

    override suspend fun clearUserTable() {
        userDao.clearUserTable()
    }
}