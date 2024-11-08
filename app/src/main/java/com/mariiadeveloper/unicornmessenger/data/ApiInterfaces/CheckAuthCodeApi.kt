package com.mariiadeveloper.unicornmessenger.data.ApiInterfaces

import com.mariiadeveloper.unicornmessenger.data.dto.request.CheckAuthCodeRequestDto
import com.mariiadeveloper.unicornmessenger.data.dto.request.RegisterRequestDto
import com.mariiadeveloper.unicornmessenger.data.dto.request.SendAuthCodeRequestDto
import com.mariiadeveloper.unicornmessenger.data.dto.response.CheckAuthCodeResponseDto
import com.mariiadeveloper.unicornmessenger.data.dto.response.RegisterResponseDto
import com.mariiadeveloper.unicornmessenger.data.dto.response.SendAuthCodeResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// запрос на отправку кода подтверждения номера телефона
interface CheckAuthCodeApi {
    @POST("api/v1/users/send-auth-code/")
    fun checkAuthCode(
        // в теле запрса - номер телефона и код подтверждения
        @Body sendAuthCodeRequest: CheckAuthCodeRequestDto
    ): Call<CheckAuthCodeResponseDto>
}



