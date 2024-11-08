package com.mariiadeveloper.unicornmessenger.presentation.screen.state

// чтобы любой класс (EmailUpdated, PasswordUpdated) можно было выдать за класс LoginScreenEvent
sealed class CodeScreenEvent {
    // data-класс для изменения email
    data class CodeUpdated(val newCode: String): CodeScreenEvent()
}

data class CodeScreenState(
    val code: String = "",
)
