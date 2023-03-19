package com.ndoudou.tp1.di

import com.ndoudou.tp1.domain.repository.UserRepository
import com.ndoudou.tp1.domain.usecase.GetUser
import com.ndoudou.tp1.domain.usecase.GetUsers
import com.ndoudou.tp1.domain.usecase.InsertUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UserUseCaseModule {

    @Provides
    fun provideGetUsersUseCase(userRepository: UserRepository) : GetUsers{
        return GetUsers(userRepository)
    }

    @Provides
    fun provideInsertUserUseCase(userRepository: UserRepository): InsertUser{
        return InsertUser(userRepository)
    }

    @Provides
    fun provideGetUserByIdUseCase(userRepository: UserRepository) : GetUser{
        return GetUser(userRepository)
    }
}