package com.mariiadeveloper.unicornmessenger.presentation.screen.main.state

import com.mariiadeveloper.unicornmessenger.domain.Chat


// состояние главного экрана: отображаем или список всех чатов или переписку в однм чате
sealed class UnicornMessangerScreenState {
    object Initial:UnicornMessangerScreenState ()
    data class Chats(val posts: List<Chat>) : UnicornMessangerScreenState()
}