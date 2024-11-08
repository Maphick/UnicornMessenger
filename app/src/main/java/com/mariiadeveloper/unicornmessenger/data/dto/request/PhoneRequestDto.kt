package com.mariiadeveloper.unicornmessenger.data.dto.request

import com.google.gson.annotations.SerializedName

// дата-класс для отправки номера телефона с целью получения кода авторизации
data class PhoneRequestDto(
    @SerializedName("phone")
    val phone: String,
)