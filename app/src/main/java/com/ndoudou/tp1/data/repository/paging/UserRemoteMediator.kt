package com.ndoudou.tp1.data.repository.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ndoudou.tp1.data.local.dao.UserDao
import com.ndoudou.tp1.data.local.database.DataBase
import com.ndoudou.tp1.data.local.entity.UserEntity
import com.ndoudou.tp1.data.local.entity.UserRemoteKeysEntity
import com.ndoudou.tp1.data.remote.api.Api
import kotlinx.coroutines.delay

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val api: Api,
    private val userDao: UserDao
) : RemoteMediator<Int, UserEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {

        return try {

            val currentPage = when (loadType) {

                LoadType.REFRESH -> {
                    getNextPageClosestToCurrentPosition(state = state)?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeysPreviousPage = getPreviousPageForTheFirstItem(state = state)
                    delay(1000)
                    val previousPage = remoteKeysPreviousPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeysPreviousPage != null
                    )
                    previousPage
                }

                LoadType.APPEND -> {
                    val remoteKeysNextPage = getNextPageForTheLastItem(state = state)
                    delay(1000)
                    val nextPage = remoteKeysNextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeysNextPage != null
                    )
                    nextPage
                }
            }

            Log.d("PAGE",currentPage.toString())


            val users = api.getUsers(page = currentPage).data

            val endOfPaginationReached = users.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            try {

                if (loadType == LoadType.REFRESH) {
                    deleteCache()
                }

                val keys = users.map{ user ->
                        UserRemoteKeysEntity(
                            id = user.id,
                            nextPage = nextPage,
                            previousPage = prevPage
                        )
                }

                userDao.insertUserRemoteKeys(keys = keys)

                userDao.insertUsers(users = users.map { user-> user.toUserEntity() }  )


            } catch (exp: Exception) {
                exp.stackTrace.forEach { stackTraceElement ->
                    Log.d(
                        "exception",
                        "file name: ${stackTraceElement.fileName} class name: ${stackTraceElement.className}"
                    )
                }
            }
            delay(1000)
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (exp: Exception) {
            MediatorResult.Error(exp)
        }

    }

    private suspend fun getNextPageClosestToCurrentPosition(state: PagingState<Int, UserEntity>): Int? {
        val position = state.anchorPosition
        val entity = position?.let { state.closestItemToPosition(it) }
        return entity?.id?.let { userDao.getUserRemoteKey(id = it).nextPage }
    }

    private suspend fun getPreviousPageForTheFirstItem(state: PagingState<Int, UserEntity>): Int? {
        val loadResult = state.pages.firstOrNull { it.data.isNotEmpty() }
        val entity = loadResult?.data?.firstOrNull()
        return entity?.let { userEntity -> userDao.getUserRemoteKey(id = userEntity.id).previousPage }
    }

    private suspend fun getNextPageForTheLastItem(state: PagingState<Int, UserEntity>): Int? {
        val loadResult = state.pages.lastOrNull { it.data.isNotEmpty() }
        val entity = loadResult?.data?.lastOrNull()
        return entity?.let { userEntity -> userDao.getUserRemoteKey(id = userEntity.id).nextPage }
    }

    private suspend fun deleteCache() {
        userDao.clearUserTable()
        userDao.clearUserRemoteKeysTable()
    }

}