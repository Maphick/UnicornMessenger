package com.mariiadeveloper.unicornmessenger.presentation.screen.state

sealed class RegisterScreenEvent{
    data class PhoneUpdate(val newPhone: String): RegisterScreenEvent()
    data class NameUpdate(val newName: String): RegisterScreenEvent()
    data class UsernameUpdated(val newUsername: String): RegisterScreenEvent()
    class SendRegisterPressed: RegisterScreenEvent()
}

data class RegisterScreenState(
    var phone: String = "",
    val name: String = "",
    val username: String = ""
)
