package com.mariiadeveloper.unicornmessenger.data.ApiInterfaces

import com.mariiadeveloper.unicornmessenger.data.dto.response.CheckJwtResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

//  отправка запроса проверки валидности аксесс-токена?
interface CheckJwtApi {
    @GET("api/v1/users/check-jwt/")
    fun checkJwt(
        @Header("Authorization")authorization: String
    ): Call<CheckJwtResponseDto>
}
