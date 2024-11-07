package com.mariiadeveloper.unicornmessenger.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
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
import com.mariiadeveloper.unicornmessenger.presentation.navigation.Screen
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.LoginScreenEvent
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.RegisterScreenEvent
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.RegisterScreenState
import com.mariiadeveloper.unicornmessenger.presentation.screen.viewmodel.RegisterScreenViewModel
import com.mariiadeveloper.unicornmessenger.presentation.ui.component.StyledButton
import com.mariiadeveloper.unicornmessenger.presentation.ui.theme.UnicornMessengerTheme

val context = App.instance.appContext

@Composable
fun RegisterScreen(
    // чтобы не передавать нав хост контроллер, тк могут быть утечки памяти
    onNavigateTo: (Screen) -> Unit = {},
    // переход на главный экран
            onNavigateToMainScreen: (Screen) -> Unit = {}
)
{
    val viewModel = viewModel<RegisterScreenViewModel>()
    RegisterView(
        state = viewModel.state,
        onEvent = viewModel::onEvent,
        onNavigateTo = onNavigateTo,
        onNavigateToMainScreen = onNavigateToMainScreen,
    )
}

private const val s = "fevfwebvgrebefrbgr"

@Composable
fun RegisterView(
    state: RegisterScreenState = RegisterScreenState(),
    onEvent: (RegisterScreenEvent) -> Int = {
      0
    },
    onNavigateTo: (Screen) -> Unit = {},
    // переход на главный экран
    onNavigateToMainScreen: (Screen) -> Unit = {},
)
{
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            modifier = Modifier
                .padding(top = 100.dp),
            fontSize = 30.sp,
            text = stringResource(R.string.app_name),
            fontFamily = FontFamily(Font(DeviceFontFamilyName("sans-serif"))),
            color = MaterialTheme.colorScheme.onBackground
        )
        OutlinedTextField(
            value = state.phone,
            onValueChange = {
                onEvent(RegisterScreenEvent.PhoneUpdate(it))
            },
            leadingIcon = {
                Icon(
                    painter = rememberVectorPainter(
                        image = Icons.Outlined.Phone
                    ),
                    contentDescription = null
                )
            },
            modifier = Modifier
                .padding(
                    top = 10.dp
                ),
            placeholder = {
                Text(
                    text = stringResource(R.string.enter_phone),
                    fontFamily = FontFamily(Font(DeviceFontFamilyName("sans-serif"))),
                )
            }
        )
        OutlinedTextField(
            value = state.name,
            onValueChange = {
                onEvent(RegisterScreenEvent.NameUpdate(it))
            },
            leadingIcon = {
                Icon(
                    painter = rememberVectorPainter(
                        image = Icons.Outlined.Person
                    ),
                    contentDescription = null
                )
            },
            modifier = Modifier
                .padding(
                    top = 10.dp
                ),
            placeholder = {
                Text(
                    text = stringResource(R.string.enter_name),
                    fontFamily = FontFamily(Font(DeviceFontFamilyName("sans-serif"))),
                )
            }
        )
        OutlinedTextField(
            value = state.username,
            onValueChange = {
                onEvent(RegisterScreenEvent.UsernameUpdated(it))
            },
            leadingIcon = {
                Icon(
                    painter = rememberVectorPainter(
                        image = Icons.Outlined.Face
                    ),
                    contentDescription = null
                )
            },
            modifier = Modifier
                .padding(
                    top = 10.dp
                ),
            placeholder = {
                Text(
                    text = stringResource(R.string.enter_username),
                    fontFamily = FontFamily(Font(DeviceFontFamilyName("sans-serif"))),
                )
            }
        )
        StyledButton(
            modifier = Modifier
                .padding(
                    top = 50.dp
                )
                .width(280.dp)
                .height(50.dp),
            onClick = {
                // зарегистрироваться
                val isSuccesedRegister= onEvent(RegisterScreenEvent.SendRegisterPressed())
                // прошла ли регистрация успешно
                showToast(isSuccesedRegister)
                // если регистрация прошла успешно - переходим на страницу с чатами
                if (isSuccesedRegister == 0)
                    onNavigateToMainScreen(Screen.Main)

            },
            content =
            {
                Text(
                    text = stringResource(R.string.register),
                    fontSize = 19.sp,
                    fontFamily = FontFamily(Font(DeviceFontFamilyName("sans-serif"))),
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
            }
        )
        Text(
            text = stringResource(R.string.already_have_an_account),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(DeviceFontFamilyName("sans-serif"))),
            modifier = Modifier
                .padding(
                    top = 20.dp
                )
                .clickable {
                    onNavigateTo(Screen.Login)
                }
        )
    }
}




fun showToast(isSuccesedRegister: Int)
{
    //  если удачно - переходим на главный экран
    when (isSuccesedRegister) {
        // регистрация прошла удачно
        0 ->
            Toast.makeText(
                context,
                context.getString(R.string.successed_register),
                Toast.LENGTH_LONG
            ).show()

        1 ->
            Toast.makeText(
                context,
                context.getString(R.string.user_exist),
                Toast.LENGTH_LONG
            )
        -1 -> Toast.makeText(
            context,
            context.getString(R.string.network_problem),
            Toast.LENGTH_LONG
        )

        else ->
            Toast.makeText(
                context,
                context.getString(R.string.unknown_error),
                Toast.LENGTH_LONG
            )
    }
}


@Composable
@Preview(showBackground = true)
fun RegisterScreenPreview() {
    RegisterView()
}

@Preview(showBackground = true)
@Composable
private fun  RegisterScreenPreviewLight()
{
    UnicornMessengerTheme(darkTheme = false) {
        RegisterView()
    }

}

@Preview(showBackground = false)
@Composable
private fun  RegisterScreenPreviewwDark()
{
    UnicornMessengerTheme(darkTheme = true)  {
        RegisterView()
    }
}