package com.ndoudou.tp1.presentation.interfaces

import com.ndoudou.tp1.model.User

interface Communicator {
    fun passDataCom(user: User)
}