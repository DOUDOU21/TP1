package com.ndoudou.tp1.di

import com.ndoudou.tp1.domain.repository.UserRepository
import com.ndoudou.tp1.domain.usecase.*
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

    @Provides
    fun provideDeleteUserByIdUseCase(userRepository: UserRepository) : DeleteUser{
        return DeleteUser(userRepository)
    }

    @Provides
    fun provideUpdateUserByIdUseCase(userRepository: UserRepository) : UpdateUser {
        return UpdateUser(userRepository)
    }
}