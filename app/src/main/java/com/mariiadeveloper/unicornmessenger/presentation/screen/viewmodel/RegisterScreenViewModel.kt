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
            is RegisterScreenEvent.PhoneUpdate -> state = state.copy(phone = event.newPhone)
            is RegisterScreenEvent.NameUpdate -> state = state.copy(name = event.newName)
            is RegisterScreenEvent.UsernameUpdated -> state = state.copy(username = event.newUsername)
        }
    }
}

