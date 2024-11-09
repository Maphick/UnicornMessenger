package com.mariiadeveloper.unicornmessenger.presentation.screen.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mariiadeveloper.unicornmessenger.domain.Chat
import com.mariiadeveloper.unicornmessenger.domain.ChatMessage
import com.mariiadeveloper.unicornmessenger.presentation.screen.main.state.ChatWithUserScreenState

// вью модель для одного чата
class ChatWithUserViewModel(
    chat: Chat
) : ViewModel() {
    private val _screenState = MutableLiveData<ChatWithUserScreenState>(ChatWithUserScreenState.Initial)
    val screenState: LiveData<ChatWithUserScreenState> =_screenState

    init {
        LoadMessages(chat)
    }

    private fun LoadMessages(chat: Chat)
    {
        val messages = mutableListOf<ChatMessage>().apply {
            repeat(10)
            {
                add(ChatMessage(id = it))
            }
        }
        _screenState.value = ChatWithUserScreenState.Messages(
            chat = chat,
            messages = messages
        )
    }

}