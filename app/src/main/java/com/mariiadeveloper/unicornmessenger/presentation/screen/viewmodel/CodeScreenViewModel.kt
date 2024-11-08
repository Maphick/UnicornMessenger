package com.mariiadeveloper.unicornmessenger.presentation.screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.CodeScreenEvent
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.CodeScreenState
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.LoginScreenEvent
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.LoginScreenState

class CodeScreenViewModel: ViewModel() {

    var state by mutableStateOf(CodeScreenState())
        // чтобы менять email и password только изнутри этой вью модели
        private set

    // универсальная функция
    fun onEvent(event: CodeScreenEvent)
    {
        when (event) {
            is CodeScreenEvent.CodeUpdated -> this.state = state.copy(code = event.newCode)
        }
    }

}