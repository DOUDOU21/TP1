package com.ndoudou.tp1.presentation.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ndoudou.tp1.data.local.entity.UserEntity
import com.ndoudou.tp1.data.repository.paging.UserShowLocalPagingSource
import com.ndoudou.tp1.domain.model.User
import com.ndoudou.tp1.domain.usecase.GetUser
import com.ndoudou.tp1.domain.usecase.GetUsers
import com.ndoudou.tp1.domain.usecase.InsertUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUsersUseCase: GetUsers,
    private val insertUser: InsertUser,
    private val getUser: GetUser
) : ViewModel() {

    private val _users: MutableStateFlow<List<User>?> = MutableStateFlow(null)
    val users: StateFlow<List<User>?> = _users

    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    var user: MutableStateFlow<User?> = _user

    val data = Pager(
        PagingConfig(
            pageSize = 5,
            enablePlaceholders = false,
            initialLoadSize = 5
        ),
    ) {
        UserShowLocalPagingSource(getUsersUseCase)
    }.flow.cachedIn(viewModelScope)

    fun insertUser(user: User) {
        viewModelScope.launch {
            insertUser.execute(user)
        }
    }

    fun getUserById(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                _user.value = getUser(id)
            } catch (e: java.lang.Exception) {
                Log.e("UserViewModel", e.message.toString())
            }
        }
    }


//    fun getUsers(){
//        viewModelScope.launch {
//            try {
//                _users.value = getUsersUseCase()
//            }catch (e: java.lang.Exception){
//                Log.e("UserViewModel", e.message.toString())
//            }
//        }
//    }


//    private val userLiveData: MutableLiveData<User> = MutableLiveData()
//
//    fun insertUser(user: UserEntity) {
//        viewModelScope.launch {
//            userRepository.insertUser(user)
//        }
//    }
//
//    fun deleteUser(user: UserEntity) {
//        viewModelScope.launch {
//            userRepository.deleteUser(user)
//        }
//    }
//
//    fun getAllUsers() {
//        users.set
//    }

    // Mutable LiveData which observed by LiveData
    //private val userLiveData : MutableLiveData<User> = MutableLiveData()

    // function to set the changed
//fun setData(user: User) {
//        userLiveData .value = user
//    }

    // function to get the changed data from user
//    fun getData(): MutableLiveData<User> = userLiveData
}