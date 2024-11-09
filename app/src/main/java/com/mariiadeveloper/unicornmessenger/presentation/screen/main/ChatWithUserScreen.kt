package com.mariiadeveloper.unicornmessenger.presentation.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mariiadeveloper.unicornmessenger.R
import com.mariiadeveloper.unicornmessenger.domain.Chat
import com.mariiadeveloper.unicornmessenger.domain.ChatMessage
import com.mariiadeveloper.unicornmessenger.presentation.screen.main.state.ChatWithUserScreenState
import com.mariiadeveloper.unicornmessenger.presentation.screen.main.viewmodel.ChatWithUserViewModel
import com.mariiadeveloper.unicornmessenger.presentation.screen.main.viewmodel.ChatWithUserViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatWithUserScreen(
    onBackPressed: () -> Unit,
    chat: Chat
) {
    val viewModel: ChatWithUserViewModel = viewModel(
        factory = ChatWithUserViewModelFactory(chat)
    )
    val screenState = viewModel.screenState.observeAsState(ChatWithUserScreenState.Initial)
    val currentState = screenState.value
    Log.d("TEST_TEST", "CommentsScreen")

    if (currentState is ChatWithUserScreenState.Messages) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        // containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    title = {
                        Text(
                            text = "Comments for FeedPost id: ${currentState.chat.id}",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                )

            }
        )
        { paddingsValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingsValues),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 72.dp
                )
            ) {
                items(
                    items = currentState.messages,
                    key = { it.id }
                )
                { comment ->
                    MessageItem(message = comment)
                }
            }
        }
    }
}

@Composable
private fun MessageItem(
    message: ChatMessage
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp
            )
    )
    {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.repoter_avatar),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = "${message.reporterName} MessageId: ${message.id}",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = message.messageText,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = message.publicationDate,
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 12.sp
            )
        }
    }
}

@Preview
@Composable
private fun PreviewComment() {
        MessageItem(message = ChatMessage(id = 0))

}