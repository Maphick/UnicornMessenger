package com.mariiadeveloper.unicornmessenger.presentation.navigation

sealed class Screens(
    val route: String
) {
    object Home : Screens(ROUTE_HOME)
    object Messages : Screens(ROUTE_MESSAGES)
    object Chats : Screens(ROUTE_CHATS)
    object Profile : Screens(ROUTE_PROFILE)

    private companion object {
        const val ROUTE_HOME = "home"
        const val ROUTE_MESSAGES = "messages"
        const val ROUTE_CHATS = "chats"
        const val ROUTE_PROFILE = "profile"
    }
}