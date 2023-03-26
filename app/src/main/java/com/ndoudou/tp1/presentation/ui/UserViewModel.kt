package com.ndoudou.tp1.presentation.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ndoudou.tp1.domain.model.User
import com.ndoudou.tp1.domain.usecase.*
import com.ndoudou.tp1.utils.PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _users: MutableStateFlow<List<User>?> = MutableStateFlow(null)
    val users: StateFlow<List<User>?> = _users

    private val _userState: MutableStateFlow<PagingData<User>> =
        MutableStateFlow(value = PagingData.empty())
    val userState: StateFlow<PagingData<User>>
        get() = _userState

    private val _user = MutableLiveData<User>()
    var user: MutableLiveData<User> = _user

    fun getUsers() {
        viewModelScope.launch {
            userUseCases.getUsersPage(pageSize = PAGE_SIZE)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _userState.value = it
                }
        }
    }

    fun insertUser(user: User) {
        viewModelScope.launch {
            userUseCases.insertUser.execute(user)
        }
    }

    fun getUserById(id: Int) {
        val userObservable = userUseCases.getUser(id)
        userObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { user ->
                _user.value = user
            }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            userUseCases.deleteUser.execute(user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            userUseCases.updateUser.execute(user)
        }
    }





//    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
//    var user: MutableStateFlow<User?> = _user


//    val data = Pager(
//        PagingConfig(
//            pageSize = 5,
//            enablePlaceholders = false,
//            initialLoadSize = 5
//        ),
//    ) {
//        UserLocalPagingSource(getUsersUseCase)
//    }.flow.cachedIn(viewModelScope)

//    fun getUserById(id: Int) {
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                _user.value = userUseCases.getUser(id)
//            } catch (e: java.lang.Exception) {
//                Log.e("UserViewModel", e.message.toString())
//            }
//        }
//    }

}