package com.ndoudou.tp1.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.ndoudou.tp1.data.local.entity.UserEntity
import com.ndoudou.tp1.data.local.entity.UserRemoteKeysEntity
import com.ndoudou.tp1.data.remote.dto.UserDto
import com.ndoudou.tp1.domain.model.User
import com.ndoudou.tp1.domain.model.UserRemoteKey
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface UserRepository {

    suspend fun insertUser(user: User): Long
    suspend fun insertUsers(users: List<User>)
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
    suspend fun getUsers(): List<User>
    suspend fun getUserRemoteKey(id: Int): UserRemoteKey
    suspend fun insertUserRemoteKeys(keys: List<UserRemoteKey>)
    suspend fun clearUserRemoteKeysTable()
    suspend fun clearUserTable()

    /**
     *  Get the list of users with pagination from a local data source
     *
     *  @param page [Int] The amount of user to be loaded  per page
     *  @return [List]<[User]>
     */
    suspend fun getUsers(page: Int): List<User>

    /**
     * Get the detail about a user
     *
     * @param id [Int] the id of the user
     * @return [Single]<[User]>
     */
    fun getUserById(id: Int): Single<User>

    /**
     *  Get the list of users with pagination from a local data source
     *
     *  @param limit [Int] the maximum number of results to return in a single page
     *  @param offset [Int] the index of the first result to return
     *  @return [List]<[User]>
     */
    suspend fun getPagedList(limit: Int, offset: Int): List<User>

    /**
     *  Get the list of users with pagination from a remote data source
     *
     *  @param pageSize [Int] The amount of user to be loaded  per page
     *  @return [Flow]<[PagingData]<[User]>>
     */
    suspend fun getUsersPage(pageSize: Int): Flow<PagingData<User>>

}