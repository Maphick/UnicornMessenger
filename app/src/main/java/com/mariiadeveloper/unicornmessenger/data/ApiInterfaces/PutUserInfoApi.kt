package com.mariiadeveloper.unicornmessenger.data.ApiInterfaces

import com.mariiadeveloper.unicornmessenger.data.dto.request.PutUserInfoRequestDto
import com.mariiadeveloper.unicornmessenger.data.dto.request.SendAuthCodeRequestDto
import com.mariiadeveloper.unicornmessenger.data.dto.response.PutUserInfoResponseDto
import com.mariiadeveloper.unicornmessenger.data.dto.response.SendAuthCodeResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

// запрос на отправку кода подтверждения номера телефона
interface PutUserInfoApi {
    @PUT("/api/v1/users/me/")
    fun putUserInfo(
        @Header("Authorization")authorization: String,
        // в теле запрса - номер телефона
        @Body putUserInfoRequestDto: PutUserInfoRequestDto
    ): Call<PutUserInfoResponseDto>
}

