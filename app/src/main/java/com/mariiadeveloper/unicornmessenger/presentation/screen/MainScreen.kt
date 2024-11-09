package com.mariiadeveloper.unicornmessenger.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mariiadeveloper.unicornmessenger.R
import com.mariiadeveloper.unicornmessenger.app.App
import com.mariiadeveloper.unicornmessenger.domain.Chat
import com.mariiadeveloper.unicornmessenger.presentation.navigation.AppNavGraph
import com.mariiadeveloper.unicornmessenger.presentation.navigation.NavigationItem
import com.mariiadeveloper.unicornmessenger.presentation.navigation.rememberNavigationState
import com.mariiadeveloper.unicornmessenger.presentation.ui.theme.BoxFilterColor

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val context = App.instance.appContext
    val navigationState = rememberNavigationState()
    // сообщения в конкретном чате
    val messagesInChat: MutableState<Chat?> = remember {
        mutableStateOf(null)
    }
    val imageSize = 30.dp
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(  BoxFilterColor),

        bottomBar = {
            NavigationBar(

                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background),
                containerColor = MaterialTheme.colorScheme.background
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

                NavigationBarItem(
                    //modifier = Modifier.background(
                        //BoxFilterColor,
                    //),
                    selected = selected,
                    onClick = {

                        if (!selected) {
                            navigationState.navigateTo(item.screen.route)
                        }
                    },
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
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant
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
                Image(
                    painter = painterResource(R.drawable.image_chat_app),
                    contentDescription = "Unicorn Messanger",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = 250.dp
                        ),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BoxFilterColor)
                )
                ChatsScreen(paddingValues = paddingValues, onChatClickListener = {
                    messagesInChat.value = it
                    navigationState.navigateToMessages()
                })
            },

            messageScreenContent = {
                    Image(
                        painter = painterResource(R.drawable.image_chat_app),
                        contentDescription = "Unicorn Messanger",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = 250.dp
                            ),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(BoxFilterColor)
                    )
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
