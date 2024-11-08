package com.mariiadeveloper.unicornmessenger.presentation.screen.state

// чтобы любой класс (EmailUpdated, PasswordUpdated) можно было выдать за класс LoginScreenEvent
sealed class LoginScreenEvent {
    // data-класс для изменения номера телефона
    data class PhoneUpdated(val newPhone: String): LoginScreenEvent()
    data class CodeUpdated(val newCode: String): LoginScreenEvent()

  class SendAuthCodePresser(): LoginScreenEvent()
}

data class LoginScreenState(
    val phone: String = "",
    val code: String = "7",
)
