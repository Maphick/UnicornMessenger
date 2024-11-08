package com.mariiadeveloper.unicornmessenger.data.ApiInterfaces

import com.mariiadeveloper.unicornmessenger.data.dto.request.RegisterRequestDto
import com.mariiadeveloper.unicornmessenger.data.dto.request.SendAuthCodeRequestDto
import com.mariiadeveloper.unicornmessenger.data.dto.response.RegisterResponseDto
import com.mariiadeveloper.unicornmessenger.data.dto.response.SendAuthCodeResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// запрос на отправку кода подтверждения номера телефона
interface SendAuthCodeApi {
    @POST("api/v1/users/send-auth-code/")
    fun sendAuthCode(
        // в теле запрса - номер телефона
        @Body sendAuthCodeRequest: SendAuthCodeRequestDto
    ): Call<SendAuthCodeResponseDto>
}

