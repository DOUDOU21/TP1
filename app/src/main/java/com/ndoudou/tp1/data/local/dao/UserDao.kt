package com.ndoudou.tp1.data.local.dao

import androidx.room.*
import com.ndoudou.tp1.data.local.entity.UserEntity
import com.ndoudou.tp1.utils.USER_TABLE_NAME

@Dao
interface UserDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity): Long

    @Update
    fun updateUser(user: UserEntity)

    @Delete
    fun deleteUser(user: UserEntity)

    @Query("SELECT * FROM $USER_TABLE_NAME WHERE id=:id")
    fun getUserById(id: Int): MutableList<UserEntity?>?

    @Query("SELECT * FROM $USER_TABLE_NAME")
    suspend fun getUsers(): List<UserEntity>

    @Query("SELECT * FROM $USER_TABLE_NAME ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getPagedList(limit: Int, offset: Int): List<UserEntity>
}