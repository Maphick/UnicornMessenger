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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.joelkanyi.jcomposecountrycodepicker.component.KomposeCountryCodePicker

import com.mariiadeveloper.unicornmessenger.R
import com.mariiadeveloper.unicornmessenger.app.App
import com.mariiadeveloper.unicornmessenger.domain.Interactor.STATUS_CODE.EXIST_USER
import com.mariiadeveloper.unicornmessenger.domain.Interactor.STATUS_CODE.NOT_EXIST_USER
import com.mariiadeveloper.unicornmessenger.domain.Interactor.STATUS_CODE.OK
import com.mariiadeveloper.unicornmessenger.presentation.navigation.Screen
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.CodeScreenEvent
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.CodeScreenState
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.LoginScreenEvent
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.LoginScreenState
import com.mariiadeveloper.unicornmessenger.presentation.screen.viewmodel.CodeScreenViewModel
import com.mariiadeveloper.unicornmessenger.presentation.screen.viewmodel.LoginScreenViewModel
import com.mariiadeveloper.unicornmessenger.presentation.ui.component.StyledButton
import com.mariiadeveloper.unicornmessenger.presentation.ui.theme.UnicornMessengerTheme


// контейнер, в котором хранится вью модель и стейт
@Composable
fun CodeScreen(
    onNavigateTo: (Screen) -> Unit = {},
    onNavigateToRegisternScreen: (Screen) -> Unit = {},
    onNavigateToMainScreen: (Screen) -> Unit = {}
)
{
    val context = App.instance.appContext
    val viewModel = viewModel<CodeScreenViewModel>()
    // следим, успешно ли отправлен код
    val isCodeSuccessed by viewModel.isCodeSuccessed.observeAsState()
    // безопасный запуска корутины
    LaunchedEffect(key1 = isCodeSuccessed) {
        isCodeSuccessed.let {

            // •	Если is user exists == true, авторизуем пользователя
            when (isCodeSuccessed)
            {

                // Если is user exists == true, авторизуем пользователя
                EXIST_USER -> {
                    onNavigateTo(Screen.Main)
                    if (it != null) {
                        showAuthToast(
                            context = context,
                            isLoginSuccess = it
                        )
                    }

                }
                // новый пользователь, отправляем на регистрацию.
                NOT_EXIST_USER -> {
                    onNavigateTo(Screen.Register)
                    if (it != null) {
                        showAuthToast(
                            context = context,
                            isLoginSuccess = it
                        )
                    }

                }
                // произошла ошибка
                else -> {
                    if (it != null) {
                        showAuthToast(
                            context = context,
                            isLoginSuccess = it
                        )
                    }
                }
            }

        }
        // сбросить состояние аутентификации
        viewModel.clearCodeState()
    }
    CodeView(
        state = viewModel.state,
        // референс на функцию onEvent
        //  т.е. она автоматически будет вызываться с параметром, который будет туда передаваться
        onEvent = viewModel::onEvent,
        onCodeSent = viewModel::onCheckAuthCode,
        onNavigateTo = onNavigateTo
    )
    //onNavigateTo(Screen.Register)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeView(
    // чтобы не передавать нав хост контроллер, тк могут быть утечки памяти
    //viewModel: LoginScreenViewModel = viewModel(),
    state: CodeScreenState = CodeScreenState(),
    // lambda-функция для изменения стейта
    onEvent: (CodeScreenEvent) -> Unit = {},
    // по нажатию на кнопку отправка кода
    onCodeSent: () -> Unit = {},
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
                .padding(top = 100.dp),
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
                    top = 180.dp
                ),
            shape = RoundedCornerShape(size = 15.dp),
            value = state.code,
            // onValueChange = onEvent(LoginScreenEvent.EmailUpdated()),
            // lambda - блок
            onValueChange = {
                onEvent(CodeScreenEvent.CodeUpdated(it))
            },
            leadingIcon = {
                Icon(
                    painter = rememberVectorPainter(
                        image = Icons.Outlined.CheckCircle
                    ),
                    contentDescription = null
                )
            },
            // по умолчанию
            placeholder = {
                Text(
                    text = stringResource(R.string.enter_code),
                    fontFamily = FontFamily(Font(DeviceFontFamilyName("sans-serif"))),
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
            ),
        )
        StyledButton(
            modifier = Modifier
                .padding(
                    top = 30.dp
                )
                .width(280.dp)
                .height(50.dp),
            onClick = {
                onCodeSent()
            },
            content =
            {
                Text(
                    text = stringResource(R.string.confirm_code),
                    fontSize = 19.sp,
                    fontFamily = FontFamily(Font(DeviceFontFamilyName("sans-serif"))),
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
            }
        )

    }

}

@Composable
@Preview(showBackground = true)
fun CodeScreenPreview() {
    CodeView()
}

@Preview(showBackground = true)
@Composable
private fun  LoginScreenPreviewLight()
{
    UnicornMessengerTheme(darkTheme = false) {
        CodeView()
    }

}

@Preview(showBackground = false)
@Composable
private fun  CodeScreenPreviewwDark()
{
    UnicornMessengerTheme(darkTheme = true)  {
        CodeView()
    }
}