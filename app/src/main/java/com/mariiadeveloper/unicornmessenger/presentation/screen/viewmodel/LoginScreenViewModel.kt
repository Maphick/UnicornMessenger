package com.mariiadeveloper.unicornmessenger.presentation.screen.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makashovadev.filmsearcher.domain.IsSuccess
import com.mariiadeveloper.unicornmessenger.R
import com.mariiadeveloper.unicornmessenger.app.App
import com.mariiadeveloper.unicornmessenger.data.dto.response.CheckJwtResponseDto
import com.mariiadeveloper.unicornmessenger.data.dto.response.RegisterResponseDto
import com.mariiadeveloper.unicornmessenger.data.dto.response.SendAuthCodeResponseDto
import com.mariiadeveloper.unicornmessenger.data.settings.PreferenceProvider
import com.mariiadeveloper.unicornmessenger.domain.Interactor
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.LoginScreenEvent
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.LoginScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class LoginScreenViewModel: ViewModel() {

    //Инициализируем интерактор
    private var interactor: Interactor = App.instance.interactor
    private val preferences: PreferenceProvider = App.instance.preferences
    private val context = App.instance.appContext
    val isAuthSuccessed = MutableLiveData<Int>(Interactor.DEFAULT)

    // сбросить состояние регистрации
    fun clearRegisteredState()
    {
        isAuthSuccessed.postValue(Interactor.DEFAULT)
    }


    var state by mutableStateOf(LoginScreenState())
        private set

    // универсальная функция
    fun onEvent(event: LoginScreenEvent) {
        var isSuccesedLogin = 0
        when (event) {
            is LoginScreenEvent.PhoneUpdated -> {
                // если цифра, то добавляем
                if (event.newPhone.toIntOrNull() != null) {
                    this.state = state.copy(phone = event.newPhone)
                }
            }

            else -> {}
        }
    }



    // WORK WITH SERVER ----------------------------------------------------------------------------

    //  SEND AUTH CODE
    // phone - номер телефона, который ввел пользователь
    fun onAuth()
    {
        if (state.phone.length == 0)
        {

        }
        else {
            viewModelScope.launch {
                auth(
                    phone = state.phone,
                    country_code = state.code
                )
            }
        }
    }

    fun auth(
        phone: String,
        country_code: String
    ): Job {
        val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        return coroutineScope.launch {
            // сохранить телефон и код страны в натсройках
            savePhoneToPreferences(phone = phone)
            saveCountryCodeToPreferences(country_code = country_code)

            val result = interactor.sendAuthCode(
                country_code = country_code,
                phone = phone,
                callback = object : SendAuthCodeCallback  {
                    override fun onSuccess(sendAuthCode: SendAuthCodeResponseDto, code: Int) {
                        Log.d("SEND_AUTH_CODE", "Success")
                        isAuthSuccessed.postValue(code)
                    }

                    override fun onFailure(s: String, code: Int) {
                        Log.d("SEND_AUTH_CODE", "Fail")
                        isAuthSuccessed.postValue(code)
                    }
                },
            )
        }
    }





    // REFRESH TOKEN
    // refresh_token -
    // access_token -
    fun RefreshToken(refresh_token: String, access_token: String) {
        interactor.regfreshTokenFromApi(
            refresh_token = refresh_token,
            access_token = access_token,
            callback = object : RefreshTokenCallback {
                override fun onSuccess(registerResponseDto: RegisterResponseDto, code: Int) {
                    Log.d("REFRESH TOKEN", "Success")
                }

                override fun onFailure(s: String, code: Int) {
                    Log.d("REFRESH TOKEN", "Fail")
                }
            },
        )
    }




    //  CHECK JWT
    // bearer_token - акссесс токен, который пришел к нам от сервера после регистрации/аутентификации
   /* fun CheckJwt(bearer_token: String) {
        interactor.checkJwtFromApi(
            bearer_token = bearer_token,
            callback = object : ApiCheckJwtCallback {
            override fun onSuccess(checkJwt: CheckJwtResponseDto?) {
                Log.d("SEND_AUTH_CODE", "Success")
            }
            override fun onFailure() {
                Log.d("SEND_AUTH_CODE", "Fail")
            }
        },
        )
    }
*/
    // CALLBACKS -----------------------------------------------------------------------------------
    // интерфейсы, которые будут отвечать за коллбэки


    interface RefreshTokenCallback {
        fun onSuccess(refreshTokenResponseDto: RegisterResponseDto, code: Int)
        fun onFailure(s: String, code: Int)
    }



    interface SendAuthCodeCallback {
        fun onSuccess(sendAuthCode: SendAuthCodeResponseDto, code: Int)
        fun onFailure(s: String, code: Int)
    }


    // интерфейс, который будет отвечать за коллбэк
    interface ApiCheckJwtCallback {
        fun onSuccess(checkJwt: CheckJwtResponseDto, code: Int)
        fun onFailure(code: Int)
    }


    // ACCESS TOKEN + REFRESH TOKEN ----------------------------------------------------------------
    // методы для хранения токенов


    // Сохранить код страны
    fun saveCountryCodeToPreferences(country_code: String) {
        preferences.saveCountryCode(country_code)
    }



    // Сохранить телефон
    fun savePhoneToPreferences(phone: String) {
        preferences.savePhone(phone)
    }


    // Сохранить аксесс токен
    fun saveAccessTokenToPreferences(category: String) {
        preferences.saveAccessToken(category)
    }

    // Метод для получения аксесс токена
    fun getAccessTokenFromPreferences() = preferences.getAccessToken()

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