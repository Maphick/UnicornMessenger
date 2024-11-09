package com.mariiadeveloper.unicornmessenger.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.mariiadeveloper.unicornmessenger.R

sealed class NavigationItem(
    val screen: Screens,
    val titleResId: Int,
    val icon: Int,
) {
    object Home : NavigationItem(
        screen = Screens.Home,
        titleResId = R.string.navigation_item_main,
        icon = R.drawable.home
    )
    object Profile : NavigationItem(
        screen = Screens.Profile,
        titleResId = R.string.navigation_item_profile,
        icon =  R.drawable.person
    )
}