package com.mariiadeveloper.unicornmessenger.data.dto.response

import com.google.gson.annotations.SerializedName

// дата класс для получения ответа успех/не успех при отправке кода авторизации
data class IsSuccessResponseDto(
    @SerializedName("is_success")
    val is_success: Boolean,
)