package com.mariiadeveloper.unicornmessenger.presentation.screen.main.state

import com.mariiadeveloper.unicornmessenger.domain.Chat
import com.mariiadeveloper.unicornmessenger.domain.ChatMessage


sealed class ChatWithUserScreenState {
    object Initial : ChatWithUserScreenState()

    data class Messages(
        val chat: Chat,
        val messages: MutableList<ChatMessage>
    ) : ChatWithUserScreenState()
}