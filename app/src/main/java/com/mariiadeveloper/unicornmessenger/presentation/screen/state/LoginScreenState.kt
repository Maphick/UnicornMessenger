package com.mariiadeveloper.unicornmessenger.presentation.screen.state

// чтобы любой класс (EmailUpdated, PasswordUpdated) можно было выдать за класс LoginScreenEvent
sealed class LoginScreenEvent {
    // data-класс для изменения email
    data class EmailUpdated(val newEmail: String): LoginScreenEvent()
    data class PasswordUpdated(val newPassword: String): LoginScreenEvent()
}

data class LoginScreenState(
    val email: String = "",
    val password: String = ""
)
