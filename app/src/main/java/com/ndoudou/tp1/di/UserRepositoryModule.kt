package com.ndoudou.tp1.di

import com.ndoudou.tp1.data.local.dao.UserDao
import com.ndoudou.tp1.data.local.database.DataBase
import com.ndoudou.tp1.data.remote.api.Api
import com.ndoudou.tp1.data.repository.UserRepositoryImpl
import com.ndoudou.tp1.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserRepositoryModule {
    @Singleton
    @Provides
    fun provideUserRepository(userDao : UserDao, api : Api): UserRepository{
        return UserRepositoryImpl(userDao, api)
    }
}