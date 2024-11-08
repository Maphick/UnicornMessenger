package com.mariiadeveloper.unicornmessenger.data.dto.response

import com.google.gson.annotations.SerializedName

data class SendAuthCodeResponseDto(
    @SerializedName("is_success") val is_success: Boolean,
)
