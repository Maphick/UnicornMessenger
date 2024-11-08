package com.mariiadeveloper.unicornmessenger.presentation.screen.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariiadeveloper.unicornmessenger.app.App
import com.mariiadeveloper.unicornmessenger.data.dto.response.RegisterResponseDto
import com.mariiadeveloper.unicornmessenger.data.settings.PreferenceProvider
import com.mariiadeveloper.unicornmessenger.domain.Interactor
import com.mariiadeveloper.unicornmessenger.domain.Interactor.STATUS_CODE.DEFAULT
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.RegisterScreenEvent
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.RegisterScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class RegisterScreenViewModel: ViewModel() {


    //Инициализируем интерактор
    private var interactor: Interactor = App.instance.interactor
    private val preferences: PreferenceProvider = App.instance.preferences
    val isRegisteredSuccessed = MutableLiveData<Int>(DEFAULT)

    // сбросить состояние регистрации
    fun clearRegisteredState()
    {
        isRegisteredSuccessed.postValue(DEFAULT)
    }
    var state by mutableStateOf(RegisterScreenState())
        private set

    init {
        val country_code = getCountryCodeFromPreferences()
        val phone = getPhoneFromPreferences()
        state.phone = "+" + country_code + phone
    }



    fun onEvent(event: RegisterScreenEvent): Int {
        var isSuccesedLogin = 0
        when (event) {
            //is RegisterScreenEvent.PhoneUpdate ->
           // {
                //state = state.copy(phone = event.newPhone)
            //}
            is RegisterScreenEvent.NameUpdate -> {
                state = state.copy(name = event.newName)
            }
            is RegisterScreenEvent.UsernameUpdated -> {
                state = state.copy(username = event.newUsername)
            }

            is RegisterScreenEvent.SendRegisterPressed -> {

            }
            else -> {}
        }
        return isSuccesedLogin
    }



    fun onRegister()
    {
        viewModelScope.launch {
          register(
                phone = state.phone,
                name = state.name,
                username = state.username
            )
        }
    }


    // REGISTER
    // phone - номер телефона, который ввел пользователь
    // name -
    // username -
    private fun register(phone: String, name: String, username: String): Job {
        val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        return coroutineScope.launch {
            // результат запроса
           interactor.registerFromApi(
                phone = phone,
                name = name,
                username = username,
                callback = object : RegisterCallback {
                    override fun onSuccess(registerResponseDto: RegisterResponseDto, code: Int) {
                        Log.d("REGISTER", "Success")
                        isRegisteredSuccessed.postValue(code)
                    }

                    override fun onFailure(s: String, code: Int) {
                        Log.d("REGISTER", "Fail")
                        isRegisteredSuccessed.postValue(code)
                    }
                },
            )
            }
    }

    interface RegisterCallback {
        fun onSuccess(registerResponseDto: RegisterResponseDto, code: Int)
        fun onFailure(s: String, code: Int)
    }

    // Метод для получения кода страны
    fun getCountryCodeFromPreferences() = preferences.getCountryCode()

    // Метод для получения телефона
    fun getPhoneFromPreferences() = preferences.getPhone()

}
