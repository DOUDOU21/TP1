package com.ndoudou.tp1.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ndoudou.tp1.model.User

class UserViewModel : ViewModel() {
    // Mutable LiveData which observed by LiveData
    private val userLiveData : MutableLiveData<User> = MutableLiveData()

    // function to set the changed
    fun setData(user: User) {
        userLiveData .value = user
    }

    // function to get the changed data from user
    fun getData(): MutableLiveData<User> = userLiveData
}