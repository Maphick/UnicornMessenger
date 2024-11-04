package com.mariiadeveloper.unicornmessenger.presentation.screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginScreenViewModel: ViewModel() {


    var email by mutableStateOf("")
        //  // чтобы менять email только изнутри этой вью модели
        private set

    var password by mutableStateOf("")
        private set

    fun updateEmail(email: String)
    {
        this.email = email
    }

    fun updatePassword(password: String)
    {
        this.password = password
    }

}