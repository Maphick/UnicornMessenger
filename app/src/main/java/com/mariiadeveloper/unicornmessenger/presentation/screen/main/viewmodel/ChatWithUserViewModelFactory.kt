package com.mariiadeveloper.unicornmessenger.presentation.screen.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mariiadeveloper.unicornmessenger.domain.Chat

class ChatWithUserViewModelFactory(
    private val speechPost: Chat
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatWithUserViewModel(speechPost) as T
    }

}