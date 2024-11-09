package com.mariiadeveloper.unicornmessenger.presentation.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mariiadeveloper.unicornmessenger.R
import com.mariiadeveloper.unicornmessenger.domain.Chat
import com.mariiadeveloper.unicornmessenger.domain.StatisticItem
import com.mariiadeveloper.unicornmessenger.domain.StatisticType

// карточка одного чата
@Composable
fun ChatCard(
    modifier: Modifier = Modifier,
    chat: Chat,
    onChatClickListener: (Chat) -> Unit
) {
    Card(
        modifier = modifier

            .padding(
                8.dp
            )
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .clickable {
                    onChatClickListener(chat)
                }
            .padding(8.dp)
        ) {
            ChatHeader(chat)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = chat.contentText)
        }
    }
}



@Composable
private fun ChatHeader(chat: Chat)
{
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            painter = painterResource(id = chat.avatarResId),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(1f)
        )
        {
            Text(
                text = chat.reporterName,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
        Text(
            text = chat.publicationDate,
            color = MaterialTheme.colorScheme.onSecondary
        )

    }
}
