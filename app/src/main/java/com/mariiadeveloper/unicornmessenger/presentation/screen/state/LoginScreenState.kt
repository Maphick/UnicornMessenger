package com.mariiadeveloper.unicornmessenger.presentation.screen.state

// чтобы любой класс (EmailUpdated, PasswordUpdated) можно было выдать за класс LoginScreenEvent
sealed class LoginScreenEvent {
    // data-класс для изменения email
    data class PhoneUpdated(val newPhone: String): LoginScreenEvent()
}

data class LoginScreenState(
    val phone: String = "",
)
