package com.mariiadeveloper.unicornmessenger.data.dto.request

import com.google.gson.annotations.SerializedName

data class SendAuthCodeRequestDto(
    @SerializedName("phone") val phone: String,
)