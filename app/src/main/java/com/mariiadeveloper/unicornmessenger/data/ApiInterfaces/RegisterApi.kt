package com.mariiadeveloper.unicornmessenger.data.ApiInterfaces

import com.mariiadeveloper.unicornmessenger.data.dto.request.RegisterRequestDto
import com.mariiadeveloper.unicornmessenger.data.dto.response.RegisterResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// запрос на регистрацию нового пользователя
interface RegisterApi {
    @POST("api/v1/users/register/")
    fun register(
        // в теле запрса - номер телефона, имя пользователя и ник
        @Body registerRequest: RegisterRequestDto
    ): Call<RegisterResponseDto>
}


