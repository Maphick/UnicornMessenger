package com.mariiadeveloper.unicornmessenger.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mariiadeveloper.unicornmessenger.presentation.screen.LoginScreen
import com.mariiadeveloper.unicornmessenger.presentation.screen.MainScreen
import com.mariiadeveloper.unicornmessenger.presentation.screen.RegisterScreen
import com.mariiadeveloper.unicornmessenger.presentation.screen.viewmodel.LoginScreenViewModel


import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer


// Serialization нужен, чтобы преобразовать  data object в строки
// поэтому помечаем аннотацие @Serializable
// наследуютсяя от Screen, чтобы можно было любой выдать за Screen
sealed class Screen{
    @Serializable
    data object Login: Screen()

    @Serializable
    data object Register: Screen()

    @Serializable
    data object Main: Screen()
}

@Composable
fun MainNav(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
){
    NavHost(
        modifier = modifier ,
        navController = navHostController,
        startDestination = Screen.Login
    )
    {
        //  описание экранов
        composable<Screen.Login> {
           LoginScreen (
               onNavigateToMainScreen = {
                       navigateTo ->
                   navHostController.navigate(navigateTo)
               },
               onNavigateTo = {navigateTo ->
                   navHostController.navigate(navigateTo)
               }
           )
        }
        composable<Screen.Register> {
            RegisterScreen(
                // переход на главный экран
                onNavigateToMainScreen = {
                        navigateTo ->
                    navHostController.navigate(navigateTo)
                },
                onNavigateTo = {navigateTo ->
                    navHostController.navigate(navigateTo)
                }
            )
        }
        composable<Screen.Main> {
            MainScreen {navigateTo ->
                navHostController.navigate(navigateTo)
            }
        }

    }
}