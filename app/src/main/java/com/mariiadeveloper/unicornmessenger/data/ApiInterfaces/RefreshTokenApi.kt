package com.mariiadeveloper.unicornmessenger.data.ApiInterfaces

import com.mariiadeveloper.unicornmessenger.data.dto.request.RefreshTokenRequest
import com.mariiadeveloper.unicornmessenger.data.dto.response.IsSuccessResponseDto
import com.mariiadeveloper.unicornmessenger.data.dto.response.RegisterResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

// запрос на отправку кода подтверждения номера телефона
interface RefreshTokenApi {
    @POST("api/v1/users/refresh-token/")
    fun refreshToken(
        @Header("Authorization") authorization: String,
        @Body refreshTokenRequest: RefreshTokenRequest
    ): Call<RegisterResponseDto>
}
