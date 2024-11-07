package com.mariiadeveloper.unicornmessenger.data.dto.response

import com.google.gson.annotations.SerializedName

data class RegisterResponseDto(
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("user_id") val userId: Int
)
