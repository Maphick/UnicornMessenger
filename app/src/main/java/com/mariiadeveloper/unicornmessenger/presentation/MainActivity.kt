package com.mariiadeveloper.unicornmessenger.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.mariiadeveloper.unicornmessenger.presentation.navigation.MainNav
import com.mariiadeveloper.unicornmessenger.presentation.screen.EditProfileScreen
import com.mariiadeveloper.unicornmessenger.presentation.screen.UserProfileScreen
import com.mariiadeveloper.unicornmessenger.presentation.ui.theme.UnicornMessengerTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnicornMessengerTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorScheme.background)
                ) { innerPadding ->
                    MainContent(
                        modifier = Modifier.padding(innerPadding)
                   )
                   /* EditProfileScreen(

                    ) { }*/
                    //UserProfileScreen(

                    //)
                }
            }
        }
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier
) {
   MainNav(
       // возвращает наш  navHostController, который контролирует переключение между экранами
       // и запоминает его
       // поэтому при рекомпозиции не создается новый navHostController
       navHostController = rememberNavController(),
       modifier = modifier
   )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UnicornMessengerTheme {
        MainContent(
        )
    }
}