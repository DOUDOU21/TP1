package com.ndoudou.tp1.domain.usecase

data class UserUseCases(
    val getUser: GetUser,
    val getUsers: GetUsers,
    val insertUser: InsertUser,
    val deleteUser: DeleteUser,
    val updateUser: UpdateUser,
    val insertUsers: InsertUsers,
    val getUserRemoteKey: GetUserRemoteKey,
    val insertUserRemoteKeys: InsertUserRemoteKeys,
    val clearUserRemoteKeysTable: ClearUserRemoteKeysTable,
    val clearUserTable: ClearUserTable,
    val getUsersPage: GetUsersPage,
)
