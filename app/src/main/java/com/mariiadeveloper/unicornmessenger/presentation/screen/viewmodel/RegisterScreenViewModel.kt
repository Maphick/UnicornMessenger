package com.mariiadeveloper.unicornmessenger.presentation.screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.RegisterScreenEvent
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.RegisterScreenState


class RegisterScreenViewModel: ViewModel() {
    var state by mutableStateOf(RegisterScreenState())
        private set

    fun onEvent(event: RegisterScreenEvent)
    {
        when (event) {
            is RegisterScreenEvent.UsernameUpdated -> state = state.copy(username = event.newUsername)
            is RegisterScreenEvent.EmailUpdate -> state = state.copy(email = event.newEmail)
            is RegisterScreenEvent.PasswordUpdate -> state = state.copy(password = event.newPassword)
        }
    }
}

