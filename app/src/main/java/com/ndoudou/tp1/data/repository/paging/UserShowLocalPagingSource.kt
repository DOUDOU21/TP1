package com.ndoudou.tp1.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ndoudou.tp1.data.local.dao.UserDao
import com.ndoudou.tp1.data.local.entity.UserEntity
import com.ndoudou.tp1.domain.model.User
import com.ndoudou.tp1.domain.usecase.GetUsers
import kotlinx.coroutines.delay

class UserShowLocalPagingSource(private val getUsersUseCase: GetUsers) : PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val currentPage = params.key ?: 0
        return try {

            val entities = getUsersUseCase(params.loadSize, currentPage * params.loadSize)

            // simulate page loading
            if (currentPage != 0) delay(1000)

            LoadResult.Page(
                data = entities,
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (entities.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }
}