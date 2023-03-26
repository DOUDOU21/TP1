package com.ndoudou.tp1.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ndoudou.tp1.data.local.dao.UserDao
import com.ndoudou.tp1.data.local.entity.UserEntity
import com.ndoudou.tp1.data.local.entity.UserRemoteKeysEntity

@Database(
    entities = [
        UserEntity::class,
        UserRemoteKeysEntity::class
    ],
    version = 1,
    exportSchema = false
)
 abstract class DataBase : RoomDatabase() {
    abstract fun userDao() : UserDao

}