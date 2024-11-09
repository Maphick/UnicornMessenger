package com.mariiadeveloper.unicornmessenger.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavigationState(
    val navHostController: NavHostController
) {
    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            // настройки, которые будут применены к экранам
            // удалить все экраны между текущим и начальным
            popUpTo(navHostController.graph.findStartDestination().id)
            // в параметрах - что происходит при удалении экрана
            {
                //  при удалении экрана из бекстека у него будет сохранен стейт
                saveState = true
            }
            // удалить все копии верхнего экрана
            launchSingleTop = true
            // восстановить сохраненный стейт при переходе на экран
            restoreState = true
        }
    }

    fun navigateToComments() {
        navHostController.navigate(Screens.Home.route)
    }

}


@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}