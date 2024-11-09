package com.mariiadeveloper.unicornmessenger.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation


fun NavGraphBuilder.HomeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable () -> Unit
) {
    navigation(
        startDestination = Screens.Chats.route,
        route = Screens.Home.route
    )
    {
        // экран со свсеми чатами
        composable(Screens.Chats.route)
        {
            newsFeedScreenContent()
        }
        // экранс конкретным чатом
        composable(Screens.Messages.route)
        {
            commentsScreenContent()
        }
    }
}