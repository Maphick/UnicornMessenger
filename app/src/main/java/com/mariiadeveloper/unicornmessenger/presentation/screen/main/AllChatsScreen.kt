package com.mariiadeveloper.unicornmessenger.presentation.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mariiadeveloper.unicornmessenger.domain.Chat
import com.mariiadeveloper.unicornmessenger.presentation.screen.main.ChatCard
import com.mariiadeveloper.unicornmessenger.presentation.screen.main.state.UnicornMessangerScreenState
import com.mariiadeveloper.unicornmessenger.presentation.screen.main.viewmodel.UnicornMessengerViewModel

// экран со всеми чатами
@Composable
fun ChatsScreen(
    paddingValues: PaddingValues, onChatClickListener: (Chat) -> Unit
) {
    val viewModel: UnicornMessengerViewModel = viewModel()
    // стейт экрана: отображаем все чаты или отображаем перписку в одном чате
    val screenState = viewModel.screenState.observeAsState(UnicornMessangerScreenState.Initial)

    when (val currentState = screenState.value) {

        is UnicornMessangerScreenState.Chats -> {
            ChatList(
                paddingValues = paddingValues,
                posts = currentState.posts,
                onChatClickListener = onChatClickListener
            )
        }

        is UnicornMessangerScreenState.Initial -> {}
    }
}


//  отображение экрана со списком чатов
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ChatList(
    paddingValues: PaddingValues,
    posts: List<Chat>,
    onChatClickListener: (Chat) -> Unit
) {
    LazyColumn(
        contentPadding = paddingValues, verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = posts, key = { it.id }) { feedPost ->
                    ChatCard(chat = feedPost,
                        onChatClickListener = {
                                onChatClickListener(it)
                        },
                      )
        }
    }
}


