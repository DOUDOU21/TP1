package com.ndoudou.tp1.data.remote.api

import com.ndoudou.tp1.data.remote.dto.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    /**
     *  Get The list of user with pagination
     *
     *  @param page [Int] page number to be fetched
     *  @return <UserResponse>
     */
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int
    ): UserResponse
}