package com.mariiadeveloper.unicornmessenger.presentation.screen.state

sealed class RegisterScreenEvent{
    data class UsernameUpdated(val newUsername: String): RegisterScreenEvent()
    data class EmailUpdate(val newEmail: String): RegisterScreenEvent()
    data class PasswordUpdate(val newPassword: String): RegisterScreenEvent()
}

data class RegisterScreenState(
    val username: String = "",
    val email: String = "",
    val password: String = ""
)
