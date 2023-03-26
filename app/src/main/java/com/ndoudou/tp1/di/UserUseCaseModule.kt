package com.ndoudou.tp1.di

import com.ndoudou.tp1.domain.repository.UserRepository
import com.ndoudou.tp1.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserUseCaseModule {

    @Singleton
    @Provides
    fun providesUserUseCases(repository: UserRepository): UserUseCases{
        return UserUseCases(
            getUser = GetUser(userRepository = repository),
            getUsers = GetUsers(userRepository = repository),
            insertUser = InsertUser(userRepository = repository),
            deleteUser = DeleteUser(userRepository = repository),
            updateUser = UpdateUser(userRepository = repository),
            insertUsers = InsertUsers(userRepository = repository),
            getUserRemoteKey = GetUserRemoteKey(userRepository = repository),
            insertUserRemoteKeys = InsertUserRemoteKeys(userRepository = repository),
            clearUserRemoteKeysTable = ClearUserRemoteKeysTable(userRepository = repository),
            clearUserTable = ClearUserTable(userRepository = repository),
            getUsersPage =  GetUsersPage(userRepository = repository)
        )
    }

}