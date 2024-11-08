package com.mariiadeveloper.unicornmessenger.data.ApiInterfaces

import com.mariiadeveloper.unicornmessenger.data.dto.response.IsSuccessResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// запрос на отправку кода подтверждения номера телефона
interface SendAuthCodeApi {
    @POST("api/v1/users/send_auth_code")
    fun sendAuthCode(
        // в теле запрса - номер телефона
        @Body phone: String
    ): Call<IsSuccessResponseDto>
}

