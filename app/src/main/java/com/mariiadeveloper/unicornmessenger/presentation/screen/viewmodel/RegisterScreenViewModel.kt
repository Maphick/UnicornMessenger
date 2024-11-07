package com.mariiadeveloper.unicornmessenger.presentation.screen.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mariiadeveloper.unicornmessenger.R
import com.mariiadeveloper.unicornmessenger.app.App
import com.mariiadeveloper.unicornmessenger.data.dto.response.RegisterResponseDto
import com.mariiadeveloper.unicornmessenger.data.settings.PreferenceProvider
import com.mariiadeveloper.unicornmessenger.domain.Interactor
import com.mariiadeveloper.unicornmessenger.presentation.navigation.Screen
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.LoginScreenEvent
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.RegisterScreenEvent
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.RegisterScreenState
import com.mariiadeveloper.unicornmessenger.presentation.screen.viewmodel.LoginScreenViewModel.RegisterCallback


class RegisterScreenViewModel: ViewModel() {

    //Инициализируем интерактор
    private var interactor: Interactor = App.instance.interactor
    private val preferences: PreferenceProvider = App.instance.preferences


    var state by mutableStateOf(RegisterScreenState())
        private set

    fun onEvent(event: RegisterScreenEvent): Int {
        var isSuccesedRegister = 0
        when (event) {
            is RegisterScreenEvent.PhoneUpdate -> state = state.copy(phone = event.newPhone)
            is RegisterScreenEvent.NameUpdate -> state = state.copy(name = event.newName)
            is RegisterScreenEvent.UsernameUpdated -> state =
                state.copy(username = event.newUsername)

            is RegisterScreenEvent.SendRegisterPressed -> {
                var name = "45t43rr53242"
                var username = "52t5r42t424g"


                isSuccesedRegister = Register(
                    phone = state.phone,
                    name = state.name,
                    username = state.username
                )
            }
        }
        return isSuccesedRegister
    }



    // REGISTER
    // phone - номер телефона, который ввел пользователь
    // name -
    // username -
    fun Register(phone: String, name: String, username: String): Int {
        // результат запроса
        var answer = 0
        answer = interactor.registerFromApi(
            phone = phone,
            name = name,
            username = username,
            callback = object : RegisterCallback {
                override fun onSuccess(registerResponseDto: RegisterResponseDto?) {
                    Log.d("REGISTER", "Success")
                    //isSuccessed = true
                }

                override fun onFailure(s: String) {
                    Log.d("REGISTER", "Fail")
                    //isSuccessed = false
                }
            },
        )
        return answer
    }

}
