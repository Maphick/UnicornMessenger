package com.mariiadeveloper.unicornmessenger.data.dto.request

import com.google.gson.annotations.SerializedName

data class RegisterRequestDto(
    @SerializedName("phone") val phone: String,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String
)