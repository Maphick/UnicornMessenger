package com.mariiadeveloper.unicornmessenger.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mariiadeveloper.unicornmessenger.R
import com.mariiadeveloper.unicornmessenger.domain.Chat
import com.mariiadeveloper.unicornmessenger.presentation.screen.main.ChatCard
import com.mariiadeveloper.unicornmessenger.presentation.screen.main.state.UnicornMessangerScreenState
import com.mariiadeveloper.unicornmessenger.presentation.screen.main.viewmodel.UnicornMessengerViewModel
import com.mariiadeveloper.unicornmessenger.presentation.ui.theme.BoxFilterColor

// экран со всеми чатами
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatsScreen(
    paddingValues: PaddingValues, onChatClickListener: (Chat) -> Unit
) {
    val viewModel: UnicornMessengerViewModel = viewModel()
    // стейт экрана: отображаем все чаты или отображаем перписку в одном чате
    val screenState = viewModel.screenState.observeAsState(UnicornMessangerScreenState.Initial)

    when (val currentState = screenState.value) {

        is UnicornMessangerScreenState.Chats -> {

            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        modifier = Modifier
                            .background(BoxFilterColor),

                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            titleContentColor = MaterialTheme.colorScheme.onBackground,
                        ),
                        title = {
                            Text(
                                text = "Чаты",
                                color = MaterialTheme.colorScheme.onBackground,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        navigationIcon = {

                        }
                    )

                },
            )
            {
                Image(
                    painter = painterResource(R.drawable.image_chat_app),
                    contentDescription = "Unicorn Messanger",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = 250.dp
                        ),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BoxFilterColor)
                )
                ChatList(
                    paddingValues = paddingValues,
                    posts = currentState.posts,
                    onChatClickListener = onChatClickListener
                )
            }
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


