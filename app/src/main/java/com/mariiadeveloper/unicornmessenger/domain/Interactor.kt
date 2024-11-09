package com.mariiadeveloper.unicornmessenger.domain

import android.util.Log
import android.widget.Toast
import com.makashovadev.filmsearcher.data.Entity.ApiConstants
import com.makashovadev.filmsearcher.data.Entity.MainRepository
import com.mariiadeveloper.unicornmessenger.data.ApiInterfaces.CheckJwtApi
import com.mariiadeveloper.unicornmessenger.data.dto.response.CheckJwtResponseDto
import com.mariiadeveloper.unicornmessenger.data.settings.PreferenceProvider
import com.mariiadeveloper.unicornmessenger.presentation.screen.viewmodel.LoginScreenViewModel
import com.mariiadeveloper.unicornmessenger.utils.Converter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.mariiadeveloper.unicornmessenger.app.App
import com.mariiadeveloper.unicornmessenger.data.ApiInterfaces.CheckAuthCodeApi
import com.mariiadeveloper.unicornmessenger.data.ApiInterfaces.PutUserInfoApi
import com.mariiadeveloper.unicornmessenger.data.ApiInterfaces.RefreshTokenApi
import com.mariiadeveloper.unicornmessenger.data.ApiInterfaces.RegisterApi
import com.mariiadeveloper.unicornmessenger.data.ApiInterfaces.SendAuthCodeApi
import com.mariiadeveloper.unicornmessenger.data.Entity.User
import com.mariiadeveloper.unicornmessenger.data.dto.request.Avatar
import com.mariiadeveloper.unicornmessenger.data.dto.request.CheckAuthCodeRequestDto
import com.mariiadeveloper.unicornmessenger.data.dto.request.PutUserInfoRequestDto
import com.mariiadeveloper.unicornmessenger.data.dto.request.RefreshTokenRequest
import com.mariiadeveloper.unicornmessenger.data.dto.request.RegisterRequestDto
import com.mariiadeveloper.unicornmessenger.data.dto.request.SendAuthCodeRequestDto
import com.mariiadeveloper.unicornmessenger.data.dto.response.Avatars
import com.mariiadeveloper.unicornmessenger.data.dto.response.CheckAuthCodeResponseDto
import com.mariiadeveloper.unicornmessenger.data.dto.response.PutUserInfoResponseDto
import com.mariiadeveloper.unicornmessenger.data.dto.response.RegisterResponseDto
import com.mariiadeveloper.unicornmessenger.data.dto.response.SendAuthCodeResponseDto
import com.mariiadeveloper.unicornmessenger.presentation.screen.viewmodel.CodeScreenViewModel
import com.mariiadeveloper.unicornmessenger.presentation.screen.viewmodel.ProfileScreenViewModel
import com.mariiadeveloper.unicornmessenger.presentation.screen.viewmodel.RegisterScreenViewModel
import okhttp3.OkHttpClient
import java.util.Calendar


class Interactor() {
    private val context = App.instance.applicationContext
    var preferences = PreferenceProvider(context)

    // METHODS WITHOUT AUTH HEADER -----------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    // POST
    // -----------------------
    // 00000000000000000000000
    // -----------------------
    // /api/v1/users/send-auth-code/
    // -----------------------
    // Аутентификация. Получить код подтверждения по номеру телефона
    // -----------------------
    // Function to send the auth code
    fun sendAuthCode(
        callback: LoginScreenViewModel.SendAuthCodeCallback,
        country_code: String,
        phone: String,
        ) {
        val phone_number = "+" + country_code + phone
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val sendAuthCodeApi = retrofit.create(SendAuthCodeApi::class.java)
        val sendAuthCodeRequest = SendAuthCodeRequestDto(phone_number)
        val call =  sendAuthCodeApi.sendAuthCode(sendAuthCodeRequest)
        call.enqueue(object : Callback<SendAuthCodeResponseDto> {
            override fun onResponse(
                call: Call<SendAuthCodeResponseDto>,
                response: Response<SendAuthCodeResponseDto>
            ) {
                if (response.isSuccessful) {
                    Log.d("SendAuthCodeApi", "Auth code was sent: ${response.body()}")
                    // Convert the response to your desired data class
                    val sendAuthCode = response.body()
                    if (sendAuthCode != null) {
                        sendAuthCode.is_success
                        //  успешный запрос
                        callback.onSuccess(sendAuthCode, OK)
                    }
                    else {
                        //  неизвестная ошибка
                        callback.onSuccess(SendAuthCodeResponseDto(false), UNKNOWN_ERROR)
                    }

                } else {
                    Log.e(
                        "SendAuthCodeApi",
                        "Error sending auth code: ${response.errorBody()?.string()}"
                    )
                    callback.onFailure(
                        response.errorBody()?.string() ?: "Unknown error",
                        UN_AUTHORIZED
                    )
                    // не авторизован

                }
            }

            override fun onFailure(call: Call<SendAuthCodeResponseDto>, t: Throwable) {
                Log.e("SendAuthCodeApi", "Error sending auth code: ${t.message}")
                callback.onFailure(
                    t.message?: "Network error",
                    NETWORK_ERROR

                )
            }
        })

    }


    // POST
    // -----------------------
    // 00000000000000000000000
    // -----------------------
    // /api/v1/users/send-auth-code/
    // -----------------------
    // Аутентификация. Получить код подтверждения по номеру телефона
    // -----------------------
    /*fun sendAuthCode2(callback: LoginScreenViewModel.ApiIsSuccessCallback, phone: String){
            val sendAuthCodeApi = Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL) // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create()) // Or other converter you prefer
                .build()
                .create(SendAuthCodeApi::class.java)
        //val phone = PhoneDto(phone = phone)


        val phone = mapOf(
            "phone" to phone
        )


        sendAuthCodeApi.sendAuthCode(phone).enqueue(object : Callback<IsSuccessResponseDto> {
                override fun onResponse(call: Call<IsSuccessResponseDto>, response: Response<IsSuccessResponseDto) {
                    if (response.isSuccessful) {
                        Log.d("SendAuthCodeApi", "Auth code was send: ${response.body()}")
                        callback.onSuccess(response.body()?.let { Converter.convertApiToIsSuccessDto(it) })
                    } else {
                        Log.e("SendAuthCodeApi", "Error sending auth code: ${response.errorBody()?.string()}")
                        callback.onFailure(
                            response.errorBody()?.string() ?: "Unknown error"
                        )

                    }
                }
            override fun onFailure(p0: Call<IsSuccessResponseDto>, p1: Throwable) {
                Log.e("SendAuthCodeApi", "Error sending auth code: ${p1.message}")
            }
        })

        }*/


    // POST
    // -----------------------
    // 00000000000000000000000
    // -----------------------
    // /api/v1/users/check-auth-code/
    // -----------------------
    // Аутентификация. Отправить код подтверждения на сервер и получить токены доступа
    // -----------------------
    fun checkAuthCodeFromApi(
        callback: CodeScreenViewModel.CheckAuthCodeCallback,
        phone: String,
        code: String,
        ) {

            val retrofit = Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val checkAuthCodeApi = retrofit.create(CheckAuthCodeApi::class.java)
            val checkAuthCodeRequest = CheckAuthCodeRequestDto(phone = phone, code = code)
            val call = checkAuthCodeApi.checkAuthCode(checkAuthCodeRequest)
            call.enqueue(object : Callback<CheckAuthCodeResponseDto> {
                override fun onResponse(
                    call: Call<CheckAuthCodeResponseDto>,
                    response: Response<CheckAuthCodeResponseDto>
                ) {
                    if (response.isSuccessful) {
                        Log.d("SendAuthCodeApi", "Auth code was sent: ${response.body()}")
                        // Convert the response to your desired data class
                        val checkAuthCode = response.body()
                        if (checkAuthCode != null) {

                            if (checkAuthCode.is_user_exists) {
                                // такой пользователь уже существует
                                // авторизуем пользоваткля
                                callback.onSuccess(checkAuthCode, EXIST_USER)
                            }
                            else
                            {
                                // новый пользователь
                                // отправляем на регистрацию
                                callback.onSuccess(checkAuthCode, NOT_EXIST_USER)
                            }
                        }
                        else {
                            //  неизвестная ошибка
                            callback.onSuccess(CheckAuthCodeResponseDto("","",0,false), UNKNOWN_ERROR)
                        }

                    } else {
                        // неправильный код подтверждения
                        Log.e(
                            "SendAuthCodeApi",
                            "Error sending auth code: ${response.errorBody()?.string()}"
                        )
                        callback.onFailure(
                            response.errorBody()?.string() ?: "Unknown error",
                            CONFIRM_CODE_ERROR
                        )
                        // не авторизован

                    }
                }

                override fun onFailure(call: Call<CheckAuthCodeResponseDto>, t: Throwable) {
                    Log.e("SendAuthCodeApi", "Error sending auth code: ${t.message}")
                    callback.onFailure(
                        t.message?: "Network error",
                        NETWORK_ERROR

                    )
                }
            })
    }

    // POST
    // -----------------------
    // 00000000000000000000000
    // -----------------------
    // /api/v1/users/register/
    // -----------------------
    //  Регистрация. Отправить телефон, имя пользователя и ник на сервер и получить токены доступа
    // -----------------------
    fun registerFromApi(
        callback: RegisterScreenViewModel.RegisterCallback,
        phone: String,
        name: String,
        username: String
    ) {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val registerApi = retrofit.create(RegisterApi::class.java)
        val registerRequest = RegisterRequestDto(phone, name, username)
        val call = registerApi.register(registerRequest)
        call.enqueue(object : Callback<RegisterResponseDto> {
            override fun onResponse(
                call: Call<RegisterResponseDto>,
                response: Response<RegisterResponseDto>
            ) {
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    Log.d("RegisterApi", "Registration successful: $registerResponse")
                    // сохранение аксесс токена
                    if (registerResponse != null) {
                        saveAccessTokenToPreferences(registerResponse.accessToken)
                    }
                    // сохранение рефреш токена
                    if (registerResponse != null) {
                        saveRefreshTokenToPreferences(registerResponse.refreshToken)
                    }
                    // фиксируем время последнего обновления
                    val currentTime = Calendar.getInstance().timeInMillis
                    saveLastUpdateTimeToPreferences(currentTime)
                    // успешная регистрация
                    if (registerResponse != null) {
                        //  успешный запрос
                        callback.onSuccess(registerResponse, OK)
                    }
                    else {
                        callback.onSuccess(RegisterResponseDto(), UNKNOWN_ERROR)
                    }
                } else {
                    Log.e(
                        "RegisterApi",
                        "Error during registration: ${response.errorBody()?.string()}"
                    )
                    callback.onFailure(
                        response.errorBody()?.string() ?: "Unknown error",
                        response.code()

                    )
                    // такой пользователь уже есть
                }

            }

            override fun onFailure(call: Call<RegisterResponseDto>, t: Throwable) {
                Log.e("RegisterApi", "Network error during registration: ${t.message}")
                callback.onFailure(
                    t.message ?: "Network error",
                    NETWORK_ERROR
                )
                // проблемки с сетью
            }
        })
    }


    // METHODS USING AUTH HEADER -------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------


    // POST
    // -----------------------
    // 00000000000000000000000
    // -----------------------
    // /api/v1/users/refresh-token/
    // -----------------------
    // Запрос на обновление аксесс токена
    // -----------------------
    fun regfreshTokenFromApi(
        callback: LoginScreenViewModel.RefreshTokenCallback,
        access_token: String,
        refresh_token: String,
    ) {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val newRequest = request.newBuilder()
                    .addHeader("Authorization", "Bearer " + access_token)
                    .build()
                chain.proceed(newRequest)
            }
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        val refreshTokenApi = retrofit.create(RefreshTokenApi::class.java)
        fun refreshToken(refreshToken: String, callback: (RegisterResponseDto?, String?) -> Unit) {
            val refreshTokenRequest = RefreshTokenRequest(refreshToken)
            val call = refreshTokenApi.refreshToken(
                "Bearer " + access_token,
                refreshTokenRequest
            ) // Replace with your actual Bearer token
            call.enqueue(object : Callback<RegisterResponseDto> {
                override fun onResponse(
                    call: Call<RegisterResponseDto>,
                    response: Response<RegisterResponseDto>
                ) {
                    if (response.isSuccessful) {
                        val refreshTokenResponse = response.body()
                        Log.d("RefreshTokenApi", "Refresh token successful: $refreshTokenResponse")
                        callback(refreshTokenResponse, null)
                    } else {
                        Log.e(
                            "RefreshTokenApi",
                            "Error during refresh token: ${response.errorBody()?.string()}"
                        )
                        callback(null, response.errorBody()?.string() ?: "Unknown error")
                    }
                }

                override fun onFailure(call: Call<RegisterResponseDto>, t: Throwable) {
                    Log.e("RefreshTokenApi", "Network error during refresh token: ${t.message}")
                    callback(null, t.message ?: "Network error")
                }
            })
        }
        }

        // GET
        // -----------------------
        // Auth type - Bearer token
        // -----------------------
        // /api/v1/users/me/.
        // -----------------------
        // Пользователь. Получить данные пользователя с сервера
        // -----------------------
        fun getUserDataFromApi() {

        }

        // PUT
        // -----------------------
        // Auth type - Bearer token
        // -----------------------
        // /api/v1/users/me/.
        // -----------------------
        // Пользователь. Отправить данные пользователя на сервер
        // -----------------------
        fun putUserInfoFromApi(
            callback: ProfileScreenViewModel.ProfileCallback,
            user: User,
            bearer_token: String
            ) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(ApiConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) // Or other converter you prefer
                    .build()
            val putUserInfoApi = retrofit.create(PutUserInfoApi::class.java)
            val putUserInfoRequest =  PutUserInfoRequestDto(
                avatar = Avatar(
                    "", user.avatar
                ),
                username = user.username,
                birthday = user.dateOfBirth,
                city = user.city,
                instagram = user.instagram,
                vk = user.vk,
                name = user.name,
                status = user.status
            )

            val bearerToken = bearer_token
            val call = putUserInfoApi.putUserInfo(bearer_token, putUserInfoRequest)
            call.enqueue(object : Callback<PutUserInfoResponseDto> {
                override fun onResponse(
                    call: Call<PutUserInfoResponseDto>,
                    response: Response<PutUserInfoResponseDto>
                ) {
                    if (response.isSuccessful) {
                        Log.d("SendAuthCodeApi", "Auth code was sent: ${response.body()}")
                        // Convert the response to your desired data class
                        val putUserInfo = response.body()
                        if (putUserInfo != null) {
                           // sendAuthCode.is_success
                            //  успешный запрос
                            callback.onSuccess(putUserInfo, OK)
                        }
                        else {
                            //  неизвестная ошибка
                            callback.onSuccess(PutUserInfoResponseDto(Avatars("", "", "")), UNKNOWN_ERROR)
                        }

                    } else {
                        Log.e(
                            "SendAuthCodeApi",
                            "Error sending auth code: ${response.errorBody()?.string()}"
                        )
                        callback.onFailure(
                            response.errorBody()?.string() ?: "Unknown error",
                            UN_AUTHORIZED
                        )
                        // не авторизован

                    }
                }

                override fun onFailure(call: Call<PutUserInfoResponseDto>, t: Throwable) {
                    Log.e("SendAuthCodeApi", "Error sending auth code: ${t.message}")
                    callback.onFailure(
                        t.message?: "Network error",
                        NETWORK_ERROR

                    )
                }
            })

        }
        // GET
        // -----------------------
        // Auth type - Bearer token
        // -----------------------
        // /api/v1/users/check-jwt/
        // -----------------------
        // Проверка валидности токена?
        // -----------------------
        fun checkJwtFromApi(
            callback: LoginScreenViewModel.ApiCheckJwtCallback,
            bearer_token: String
        ) {
            val checkJwtApi = Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL) // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create()) // Or other converter you prefer
                .build()
                .create(CheckJwtApi::class.java)
            val bearerToken = bearer_token

            checkJwtApi.checkJwt(bearerToken).enqueue(object : Callback<CheckJwtResponseDto> {
                override fun onResponse(
                    call: Call<CheckJwtResponseDto>,
                    response: Response<CheckJwtResponseDto>
                ) {
                    val checkJwtResponse = response.body()
                    if (checkJwtResponse != null) {
                        callback.onSuccess(checkJwtResponse, OK)
                    }
                    else
                    {
                        callback.onSuccess(CheckJwtResponseDto(false, false), UNKNOWN_ERROR)
                    }
                }

                override fun onFailure(call: Call<CheckJwtResponseDto>, t: Throwable) {
                    //В случае провала вызываем другой метод коллбека
                    callback.onFailure(UNKNOWN_ERROR)
                    // Handle network errors
                    Log.e("CheckJwtApi", "Network error: ${t.message}")
                }
            })
        }


        // LAST GETTING CODE TIME SAVING ---------------------------------------------------------------
        // ---------------------------------------------------------------------------------------------

        // Метод для сохранения времени последнего обновления
        fun saveLastUpdateTimeToPreferences(time: Long) {
            preferences.saveLastUpdateTime(time)
        }

        //Метод для получения времени последнего обновления
        fun getLastUpdateTimeFromPreferences() = preferences.getLastUpdateTime()

        // SAVE ACCESS AND REFRESH TOKEN ---------------------------------------------------------------
        // ---------------------------------------------------------------------------------------------



        // Метод для сохраненя аксесс токена
        fun saveAccessTokenToPreferences(access_token: String) {
            preferences.saveAccessToken(access_token = access_token)
        }

        //Метод для получения аксесс токена
        fun geteAccessTokenroPreferences() = preferences.getAccessToken()


        // Метод для сохранения рефреш токена
        fun saveRefreshTokenToPreferences(refresh_token: String) {
            preferences.saveRefreshToken(refresh_token = refresh_token)
        }

        //Метод для получения рефреш токена
        fun geteRefreshTokenromPreferences() = preferences.getRefreshToken()

    // ответы сервера
    companion object STATUS_CODE {
        const val OK = 200
        const val UN_AUTHORIZED = 401
        const val CONFIRM_CODE_ERROR = 404
        const val UNPROCCESSABLE_ENTITY = 422
        const val NETWORK_ERROR = 500
        const val UNKNOWN_ERROR = 520
        const val DEFAULT = 0
        const val EXIST_USER = -200
        const val NOT_EXIST_USER = -300
        const val EMPTY_PHONE = -1
    }

}



