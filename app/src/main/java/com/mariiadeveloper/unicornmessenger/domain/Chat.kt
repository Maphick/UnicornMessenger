package com.mariiadeveloper.unicornmessenger.domain

import com.mariiadeveloper.unicornmessenger.R

// один чат
data class Chat(
    val id: Int = 0,
    // имя собеседника
    val reporterName: String = R.string.reporter_name.toString(),
    // время отправки
    val publicationDate: String = "14:00",
    val avatarResId: Int = R.drawable.reporter_avatar,
    val contentText: String = R.string.message_example.toString(),
)
