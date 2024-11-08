package com.mariiadeveloper.unicornmessenger.data.dto.response

import com.google.gson.annotations.SerializedName

data class CheckJwtResponseDto(
    @SerializedName("errors") val errors: Boolean,
    @SerializedName("is_valid") val is_valid: Boolean
)