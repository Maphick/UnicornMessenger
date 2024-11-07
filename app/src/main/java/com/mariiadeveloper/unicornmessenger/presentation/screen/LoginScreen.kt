package com.mariiadeveloper.unicornmessenger.presentation.screen

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.DeviceFontFamilyName
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.TableInfo

import com.mariiadeveloper.unicornmessenger.R
import com.mariiadeveloper.unicornmessenger.app.App
import com.mariiadeveloper.unicornmessenger.domain.Interactor
import com.mariiadeveloper.unicornmessenger.presentation.navigation.Screen
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.LoginScreenEvent
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.LoginScreenState
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.RegisterScreenEvent
import com.mariiadeveloper.unicornmessenger.presentation.screen.viewmodel.LoginScreenViewModel
import com.mariiadeveloper.unicornmessenger.presentation.ui.component.StyledButton
import com.mariiadeveloper.unicornmessenger.presentation.ui.theme.UnicornMessengerTheme

//Инициализируем интерактор
//private var interactor: Interactor = App.instance.interactor

// контейнер, в котором хранится вью модель и стейт
@Composable
fun LoginScreen(
    onNavigateToMainScreen : (Screen) -> Unit = {},
    onNavigateTo: (Screen) -> Unit = {}
)
{
    val viewModel = viewModel<LoginScreenViewModel>()
    LoginView(
        state = viewModel.state,
        // референс на функцию onEvent
        //  т.е. она автоматически будет вызываться с параметром, который будет туда передаваться
        onEvent = viewModel::onEvent,

        onNavigateTo = onNavigateTo
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    // чтобы не передавать нав хост контроллер, тк могут быть утечки памяти
    //viewModel: LoginScreenViewModel = viewModel(),
    state: LoginScreenState = LoginScreenState(),
    // lambda-функция для изменения стейта
    onEvent: (LoginScreenEvent) -> Int = {
        0
    },

    confirmCodeScreen: (Screen) -> Unit = {},
            onNavigateTo: (Screen) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
            )
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            modifier = Modifier
                .padding(top = 50.dp),
            fontSize = 30.sp,
            text = stringResource(R.string.app_name),
            fontFamily = FontFamily(Font(DeviceFontFamilyName("sans-serif"))),
            color = MaterialTheme.colorScheme.onBackground
        )
        Image(
            painter = painterResource(R.drawable.login_image_chat_app),
            contentDescription = "Unicorn Messanger login image",
            modifier = Modifier
                .size(200.dp)
                .padding(
                    top = 20.dp
                )
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(
                    top = 50.dp
                ),
            value = state.phone,
            // onValueChange = onEvent(LoginScreenEvent.EmailUpdated()),
            // lambda - блок
            onValueChange = {
                onEvent(LoginScreenEvent.PhoneUpdated(it))
                //getCodeByPhone()
            },
            leadingIcon = {
                Icon(
                    painter = rememberVectorPainter(
                        image = Icons.Outlined.Phone
                    ),
                    contentDescription = null
                )
            },
            // по умолчанию
            placeholder = {
                Text(
                    text = stringResource(R.string.enter_phone),
                    fontFamily = FontFamily(Font(DeviceFontFamilyName("sans-serif"))),
                )
            }
        )
        StyledButton(
            modifier = Modifier
                .padding(
                    top = 20.dp
                )
                .width(280.dp)
                .height(50.dp),
            onClick = {
                // зарегистрироваться
                val isSuccesedSendCode = onEvent(LoginScreenEvent.SendAuthCodePresser())
                // прошла ли регистрация успешно
                showToast( isSuccesedSendCode)
                // переходим на главный экран
                confirmCodeScreen
            },
            content =
            {
                Text(
                    text = stringResource(R.string.get_code),
                    fontSize = 19.sp,
                    fontFamily = FontFamily(Font(DeviceFontFamilyName("sans-serif"))),
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
            }
        )
        Text(
            text = stringResource(R.string.no_account_register),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(DeviceFontFamilyName("sans-serif"))),
            modifier = Modifier
                .padding(
                    top = 20.dp
                )
                .clickable {
                    onNavigateTo(Screen.Register)
                }
        )
    }

}

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
    LoginView()
}

@Preview(showBackground = true)
@Composable
private fun  LoginScreenPreviewLight()
{
    UnicornMessengerTheme(darkTheme = false) {
        LoginView()
    }

}

@Preview(showBackground = false)
@Composable
private fun  LoginScreenPreviewwDark()
{
    UnicornMessengerTheme(darkTheme = true)  {
        LoginView()
    }
}