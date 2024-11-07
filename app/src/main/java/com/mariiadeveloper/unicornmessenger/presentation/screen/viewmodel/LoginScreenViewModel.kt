package com.mariiadeveloper.unicornmessenger.presentation.screen.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.makashovadev.filmsearcher.domain.CheckJwt
import com.makashovadev.filmsearcher.domain.IsSuccess
import com.mariiadeveloper.unicornmessenger.R
import com.mariiadeveloper.unicornmessenger.app.App
import com.mariiadeveloper.unicornmessenger.data.dto.response.RegisterResponseDto
import com.mariiadeveloper.unicornmessenger.data.settings.PreferenceProvider
import com.mariiadeveloper.unicornmessenger.domain.Interactor
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.LoginScreenEvent
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.LoginScreenState

class LoginScreenViewModel: ViewModel() {

    //Инициализируем интерактор
    private var interactor: Interactor = App.instance.interactor
    private val preferences: PreferenceProvider = App.instance.preferences
    private val context = App.instance.appContext


    var state by mutableStateOf(LoginScreenState())
        private set

    // универсальная функция
    fun onEvent(event: LoginScreenEvent): Int {
        var isSuccesedLogin = 0
        when (event) {
            is LoginScreenEvent.PhoneUpdated -> {
                this.state = state.copy(phone = event.newPhone)
            }

            is LoginScreenEvent.SendAuthCodePresser -> {
                val refresh_token = preferences.getRefreshToken()
                val access_token = preferences.getAccessToken()

                // отправка запроса на подтверждение кода
                isSuccesedLogin = SendAuthCode(
                    phone = state.phone
                )

                /*
                RefreshToken(
                    refresh_token = refresh_token,
                    access_token = access_token
                )

                 */
            }
        }
        return  isSuccesedLogin
    }


    //val vacancesListLiveData = MutableLiveData<List<Vacancy>>()



    init {

    }
    // WORK WITH SERVER ----------------------------------------------------------------------------

    //  SEND AUTH CODE
    // phone - номер телефона, который ввел пользователь
    fun SendAuthCode(phone: String): Int {
        var isSuccesedSendAuthCode = 0
        interactor.sendAuthCode(
            phone = phone,
            callback = object : ApiIsSuccessCallback {
                override fun onSuccess(isSuccess: IsSuccess?) {
                 Log.d("SEND_AUTH_CODE", "Success")
                    isSuccesedSendAuthCode = 0
                }
                override fun onFailure(s: String) {
                    Log.d("SEND_AUTH_CODE", "Fail")
                    isSuccesedSendAuthCode = -1
                }
            },
        )
        return  isSuccesedSendAuthCode
    }

    // REFRESH TOKEN
    // refresh_token -
    // access_token -
    fun RefreshToken(refresh_token: String, access_token: String) {
        interactor.regfreshTokenFromApi(
            refresh_token = refresh_token,
            access_token = access_token,
            callback = object : RefreshTokenCallback {
                override fun onSuccess(registerResponseDto: RegisterResponseDto?) {
                    Log.d("REFRESH TOKEN", "Success")
                }

                override fun onFailure(s: String) {
                    Log.d("REFRESH TOKEN", "Fail")
                }
            },
        )
    }




    //  CHECK JWT
    // bearer_token - акссесс токен, который пришел к нам от сервера после регистрации/аутентификации
    fun CheckJwt(bearer_token: String) {
        interactor.checkJwtFromApi(
            bearer_token = bearer_token,
            callback = object : ApiCheckJwtCallback {
            override fun onSuccess(checkJwt: CheckJwt?) {
                Log.d("SEND_AUTH_CODE", "Success")
            }
            override fun onFailure() {
                Log.d("SEND_AUTH_CODE", "Fail")
            }
        },
        )
    }

    // CALLBACKS -----------------------------------------------------------------------------------
    // интерфейсы, которые будут отвечать за коллбэки


    interface RefreshTokenCallback {
        fun onSuccess(refreshTokenResponseDto: RegisterResponseDto?)
        fun onFailure(s: String)
    }

    interface RegisterCallback {
        fun onSuccess(registerResponseDto: RegisterResponseDto?)
        fun onFailure(s: String)
    }

    interface ApiIsSuccessCallback {
        fun onSuccess(isSuccess: IsSuccess?)
        fun onFailure(s: String)
    }

    // интерфейс, который будет отвечать за коллбэк
    interface ApiCheckJwtCallback {
        fun onSuccess(checkJwt: CheckJwt?)
        fun onFailure()
    }


    // ACCESS TOKEN + REFRESH TOKEN ----------------------------------------------------------------
    // методы для хранения токенов

    // Сохранить аксесс токен
    fun saveAccessTokenToPreferences(category: String) {
        preferences.saveAccessToken(category)
    }

    // Метод для получения аксесс токена
    fun getAccessTokenTFromPreferences() = preferences.getAccessToken()

    // Сохранить рефреш токен
    fun saveRefreshTokenToPreferences(category: String) {
        preferences.saveRefreshToken(category)
    }

    // Метод для получения рефреш токена
    fun getRefreshTokenTFromPreferences() = preferences.getRefreshToken()

    // Метод для сохранения времени последнего обновления
    fun saveLastUpdateTimeToPreferences(time: Long) {
        preferences.saveLastUpdateTime(time)
    }

    // Метод для получения времени последнего обновления
    fun getLastUpdateTimeFromPreferences() = preferences.getLastUpdateTime()


    // получить юзера из БД:
    //fun getUsersFromDB(): LiveData<List<User>> = repo.getAllFromDB()

}