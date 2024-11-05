package com.mariiadeveloper.unicornmessenger.presentation.screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.LoginScreenEvent
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.LoginScreenState

class LoginScreenViewModel: ViewModel() {

    var state by mutableStateOf(LoginScreenState())
        // чтобы менять email и password только изнутри этой вью модели
        private set

    // универсальная функция
    fun onEvent(event: LoginScreenEvent)
    {
        when (event) {
            is LoginScreenEvent.EmailUpdated -> this.state = state.copy(email = event.newEmail)
            is LoginScreenEvent.PasswordUpdated -> this.state = state.copy(password = event.newPassword)
        }
    }

}