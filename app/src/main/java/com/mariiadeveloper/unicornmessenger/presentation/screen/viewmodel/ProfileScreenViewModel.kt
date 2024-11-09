package com.mariiadeveloper.unicornmessenger.presentation.screen.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariiadeveloper.unicornmessenger.app.App
import com.mariiadeveloper.unicornmessenger.data.Entity.User
import com.mariiadeveloper.unicornmessenger.data.dto.response.PutUserInfoResponseDto
import com.mariiadeveloper.unicornmessenger.data.dto.response.RegisterResponseDto
import com.mariiadeveloper.unicornmessenger.data.settings.PreferenceProvider
import com.mariiadeveloper.unicornmessenger.domain.Interactor
import com.mariiadeveloper.unicornmessenger.domain.Interactor.STATUS_CODE.DEFAULT
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.ProfileScreenEvent
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.ProfileScreenState
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.RegisterScreenEvent
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.RegisterScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class ProfileScreenViewModel: ViewModel() {


    //Инициализируем интерактор
    private var interactor: Interactor = App.instance.interactor
    private val preferences: PreferenceProvider = App.instance.preferences
    val isProfileChangeSuccessed = MutableLiveData<Int>(DEFAULT)

    // сбросить состояние регистрации
    fun clearProfileState()
    {
        isProfileChangeSuccessed.postValue(DEFAULT)
    }
    var state by mutableStateOf(ProfileScreenState())
        private set

    init {

    }


    fun onEvent(event: ProfileScreenEvent): Int {
        var isSuccesedLogin = 0
        when (event) {
            //is RegisterScreenEvent.PhoneUpdate ->
           // {
                //state = state.copy(phone = event.newPhone)
            //}
            is ProfileScreenEvent.AvatarUpdated-> {
                state = state.copy(avatar = event.avatar)
            }
            is ProfileScreenEvent.UserNameUpdate-> {
                state = state.copy(userName = event.userName)
            }
            is ProfileScreenEvent.CityUpdate -> {
                state = state.copy(city = event.city)
            }
            is ProfileScreenEvent.DateOfBirthUpdated -> {
                state = state.copy(dateOfBirth = event.dateOfBirth)
            }
            else -> {}
        }
        return isSuccesedLogin
    }



    fun onChangeProfile()
    {
        viewModelScope.launch {
            val bearer_token = getAccessTokenFromPreferences()

            changeProfile(
                avatar = state.avatar,
              userName = state.userName,
              dateOfBirth = state.dateOfBirth,
              username = state.userName,
                bearer_token = bearer_token
            )
        }
    }


    // REGISTER
    // phone - номер телефона, который ввел пользователь
    // name -
    // username -
    private fun changeProfile(
        avatar: String,
        userName: String,
        dateOfBirth: String,
        username: String,
        bearer_token: String,
    ): Job {
        val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        return coroutineScope.launch {
            // результат запроса
            val user = User()
           interactor.putUserInfoFromApi(
                user = user,
               bearer_token = bearer_token,
                callback = object : ProfileCallback {
                    override fun onSuccess(putUserInfoResponseDto: PutUserInfoResponseDto, code: Int) {
                        Log.d("REGISTER", "Success")
                        isProfileChangeSuccessed.postValue(code)
                    }

                    override fun onFailure(s: String, code: Int) {
                        Log.d("REGISTER", "Fail")
                        isProfileChangeSuccessed.postValue(code)
                    }
                },
            )
            }
    }

    interface ProfileCallback {
        fun onSuccess(putUserInfoResponseDto: PutUserInfoResponseDto, code: Int)
        fun onFailure(s: String, code: Int)
    }

    // Метод для получения кода страны
    fun getCountryCodeFromPreferences() = preferences.getCountryCode()

    // Метод для получения телефона
    fun getPhoneFromPreferences() = preferences.getPhone()

    // Метод для получения аксесс токена
    fun getAccessTokenFromPreferences() = preferences.getAccessToken()

}
