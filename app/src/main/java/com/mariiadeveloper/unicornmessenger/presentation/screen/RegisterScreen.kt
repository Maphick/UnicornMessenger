package com.mariiadeveloper.unicornmessenger.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.room.util.TableInfo
import com.mariiadeveloper.unicornmessenger.presentation.navigation.Screen

@Composable
fun RegisterScreen(
    // чтобы не передавать нав хост контроллер, тк могут быть утечки памяти

    onNavigateTo: (Screen) -> Unit
)
{
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = "RegisterScreen"
        )
    }

}