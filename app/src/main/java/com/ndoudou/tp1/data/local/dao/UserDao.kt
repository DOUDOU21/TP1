package com.ndoudou.tp1.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.ndoudou.tp1.data.local.entity.UserEntity
import com.ndoudou.tp1.data.local.entity.UserRemoteKeysEntity
import com.ndoudou.tp1.utils.Resource
import com.ndoudou.tp1.utils.USER_REMOTE_KEYS
import com.ndoudou.tp1.utils.USER_TABLE_NAME
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface UserDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long
    @Update
    suspend fun updateUser(user: UserEntity)
    @Delete
    suspend fun deleteUser(user: UserEntity)
    @Query("SELECT * FROM $USER_TABLE_NAME WHERE id=:id")
    fun getUserById(id: Int): Single<UserEntity>
    @Query("SELECT * FROM $USER_TABLE_NAME")
    suspend fun getUsers(): List<UserEntity>
    @Query("SELECT * FROM $USER_TABLE_NAME")
    fun getUsersPages(): PagingSource<Int, UserEntity>
    @Query("SELECT * FROM $USER_TABLE_NAME ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getPagedList(limit: Int, offset: Int): List<UserEntity>
    @Query("DELETE FROM $USER_TABLE_NAME")
    suspend fun clearUserTable()
    @Query("SELECT * FROM $USER_REMOTE_KEYS WHERE id=:id")
    suspend fun getUserRemoteKey(id: Int): UserRemoteKeysEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserRemoteKeys(keys: List<UserRemoteKeysEntity>)
    @Query("DELETE FROM $USER_REMOTE_KEYS")
    suspend fun clearUserRemoteKeysTable()

}