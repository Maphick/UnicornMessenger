package com.mariiadeveloper.unicornmessenger.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mariiadeveloper.unicornmessenger.domain.Chat
import com.mariiadeveloper.unicornmessenger.presentation.navigation.AppNavGraph
import com.mariiadeveloper.unicornmessenger.presentation.navigation.NavigationItem
import com.mariiadeveloper.unicornmessenger.presentation.navigation.rememberNavigationState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState()
    // сообщения в конкретном чате
    val messagesInChat: MutableState<Chat?> = remember {
        mutableStateOf(null)
    }
    val imageSize = 30.dp
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(imageVector = Icons.Filled.MailOutline, contentDescription = "Чаты")
                    }
                }
            )},
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ){
            // текущий открытый экран
            val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
            //  текущий экран
            val items = listOf(
                NavigationItem.Home, NavigationItem.Profile
            )
            items.forEachIndexed { index, item ->
                val selected = navBackStackEntry?.destination?.hierarchy?.any {
                    it.route == item.screen.route
                } ?: false

                NavigationBarItem(selected = selected,
                    onClick = {

                        if (!selected) {
                            navigationState.navigateTo(item.screen.route)
                        }
                    },
                   // icon = { Icon(item.icon, contentDescription = null) },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = "",
                            modifier = Modifier
                                .size(
                                    imageSize
                                ),
                            tint = if (index == selectedItemIndex)
                                MaterialTheme.colorScheme.onSecondaryContainer
                            //MaterialTheme.colorScheme.surface
                            //MaterialTheme.colorScheme.secondaryContainer
                            //MaterialTheme.colorScheme.background
                            // MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant
                            //MaterialTheme.colorScheme.secondary
                        )
                    },


                    label = { Text(text = stringResource(id = item.titleResId)) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onSurface,
                    unselectedIconColor = Color.White,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface

                )
                )
            }
        }
    }) { paddingValues ->
        AppNavGraph(navHostController = navigationState.navHostController,

            newsFeedScreenContent = {
                ChatsScreen(paddingValues = paddingValues, onChatClickListener = {
                    messagesInChat.value = it
                    navigationState.navigateToComments()
                })
            },

            commentsScreenContent = {
                ChatWithUserScreen(
                    onBackPressed = {
                        //  интуитивное поведение приложения:
                        // если пользователь кликает назад - закрыть данный экран
                        navigationState.navHostController.popBackStack()
                    }, chat = messagesInChat.value!!
                )
            },

            profileScreenContent = {

                EditProfileScreen(

                )
            }

        )
    }
}
