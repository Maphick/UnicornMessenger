package com.mariiadeveloper.unicornmessenger.domain

import com.mariiadeveloper.unicornmessenger.R
import com.mariiadeveloper.unicornmessenger.app.App

private val context = App.instance.appContext
// один чат
data class Chat(
    val id: Int = 0,
    // имя собеседника
    val reporterName: String = context.getString(R.string.reporter_name),
    // время отправки
    val publicationDate: String = "14:00",
    val avatarResId: Int = R.drawable.reporter_avatar,
    val contentText: String = context.getString(R.string.message_example),
)
