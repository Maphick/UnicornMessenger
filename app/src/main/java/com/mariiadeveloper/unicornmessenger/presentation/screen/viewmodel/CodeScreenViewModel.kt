package com.mariiadeveloper.unicornmessenger.presentation.screen.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mariiadeveloper.unicornmessenger.app.App
import com.mariiadeveloper.unicornmessenger.data.dto.response.CheckAuthCodeResponseDto
import com.mariiadeveloper.unicornmessenger.data.dto.response.SendAuthCodeResponseDto
import com.mariiadeveloper.unicornmessenger.data.settings.PreferenceProvider
import com.mariiadeveloper.unicornmessenger.domain.Interactor
import com.mariiadeveloper.unicornmessenger.domain.Interactor.STATUS_CODE.DEFAULT
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.CodeScreenEvent
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.CodeScreenState
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.LoginScreenEvent
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.LoginScreenState
import com.mariiadeveloper.unicornmessenger.presentation.screen.viewmodel.LoginScreenViewModel.SendAuthCodeCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class CodeScreenViewModel: ViewModel() {

    private var interactor: Interactor = App.instance.interactor
    private val preferences: PreferenceProvider = App.instance.preferences
    val isCodeSuccessed = MutableLiveData<Int>(DEFAULT)

    var state by mutableStateOf(CodeScreenState())
        // чтобы менять email и password только изнутри этой вью модели
        private set

    // сбросить состояние регистрации
    fun clearCodeState()
    {
        isCodeSuccessed.postValue(DEFAULT)
    }


    // при вводе кода
    fun onEvent(event: CodeScreenEvent) {
        var isSuccesedLogin = 0
        when (event) {
            is CodeScreenEvent.CodeUpdated -> {
                // Ввод кода подтверждения должен состоять из 6 символов, только цифры.
                //if ((event.newCode.toIntOrNull() != null) &&
                    if(event.newCode.length <= 6)
                    {
                    this.state = state.copy(code = event.newCode)
                }
            }
            else -> {}
        }
    }



    // WORK WITH SERVER ----------------------------------------------------------------------------

    //  SEND AUTH CODE
    // phone - номер телефона, который ввел пользователь
    fun onCheckAuthCode()
    {
        viewModelScope.launch {
            checkAuthCode(
                auth_code = state.code,
            )
        }
    }

    fun checkAuthCode(auth_code: String): Job {
        val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        return coroutineScope.launch {
            val country_code = getCountryCodeFromPreferences()
            val phone = getPhoneFromPreferences()
            val phone_number = "+" + country_code + phone
            val result = interactor.checkAuthCodeFromApi(
                phone = phone_number,
                code = auth_code,
                callback = object : CheckAuthCodeCallback {
                    override fun onSuccess(sendAuthCode: CheckAuthCodeResponseDto, code: Int) {
                        Log.d("CHECK_AUTH_CODE", "Success")
                        isCodeSuccessed.postValue(code)
                    }

                    override fun onFailure(s: String, code: Int) {
                        Log.d("CHECK_AUTH_CODE", "Fail")
                        isCodeSuccessed.postValue(code)
                    }
                },
            )
        }
    }

    // Метод для получения кода страны
    fun getCountryCodeFromPreferences() = preferences.getCountryCode()

    // Метод для получения телефона
    fun getPhoneFromPreferences() = preferences.getPhone()

    interface CheckAuthCodeCallback {
        fun onSuccess(sendAuthCode: CheckAuthCodeResponseDto, code: Int)
        fun onFailure(s: String, code: Int)
    }



}