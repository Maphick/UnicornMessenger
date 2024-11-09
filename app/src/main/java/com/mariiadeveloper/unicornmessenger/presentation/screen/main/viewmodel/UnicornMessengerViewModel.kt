package com.mariiadeveloper.unicornmessenger.presentation.screen.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mariiadeveloper.unicornmessenger.domain.Chat
import com.mariiadeveloper.unicornmessenger.domain.StatisticItem
import com.mariiadeveloper.unicornmessenger.presentation.screen.main.state.UnicornMessangerScreenState

class UnicornMessengerViewModel : ViewModel() {

    private val sourceList = mutableListOf<Chat>().apply {
        repeat(10) {
            add(Chat(id = it))
        }
    }

    // исходный стейт
    private val initialState = UnicornMessangerScreenState.Chats(posts = sourceList)

    private val _screenState = MutableLiveData<UnicornMessangerScreenState>(initialState)
    val screenState: LiveData<UnicornMessangerScreenState> = _screenState


}