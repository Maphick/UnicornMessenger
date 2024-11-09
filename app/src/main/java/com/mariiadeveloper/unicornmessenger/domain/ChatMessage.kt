package com.mariiadeveloper.unicornmessenger.domain

import androidx.compose.ui.res.stringResource
import com.mariiadeveloper.unicornmessenger.R
import com.mariiadeveloper.unicornmessenger.app.App

private val context = App.instance.appContext
data class ChatMessage(
    val id: Int,
    val isMyMessage: Boolean = true,
    val reporterName: String = context.getString(R.string.reporter_name),
    val authorAvatarId: Int = R.drawable.repoter_avatar,
    val messageText: String = context.getString(R.string.message_example),
    val publicationDate: String = context.getString(R.string.time_example),
)
