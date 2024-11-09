package com.mariiadeveloper.unicornmessenger.presentation.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.DeviceFontFamilyName
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mariiadeveloper.unicornmessenger.R
import com.mariiadeveloper.unicornmessenger.app.App
import com.mariiadeveloper.unicornmessenger.domain.Interactor
import com.mariiadeveloper.unicornmessenger.presentation.navigation.Screen
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.RegisterScreenEvent
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.RegisterScreenState
import com.mariiadeveloper.unicornmessenger.presentation.screen.viewmodel.RegisterScreenViewModel
import com.mariiadeveloper.unicornmessenger.presentation.ui.component.StyledButton
import com.mariiadeveloper.unicornmessenger.domain.Interactor.STATUS_CODE.EXIST_USER
import com.mariiadeveloper.unicornmessenger.domain.Interactor.STATUS_CODE.NETWORK_ERROR
import com.mariiadeveloper.unicornmessenger.domain.Interactor.STATUS_CODE.NOT_EXIST_USER
import com.mariiadeveloper.unicornmessenger.domain.Interactor.STATUS_CODE.OK
import com.mariiadeveloper.unicornmessenger.domain.Interactor.STATUS_CODE.UNPROCCESSABLE_ENTITY
import com.mariiadeveloper.unicornmessenger.domain.Interactor.STATUS_CODE.UN_AUTHORIZED

@Composable
fun RegisterScreen(


    // чтобы не передавать нав хост контроллер, тк могут быть утечки памяти
    onNavigateTo: (Screen) -> Unit = {},
    // переход на главный экран
    onNavigateToMainScreen: (Screen) -> Unit = {}
) {
    val context = App.instance.appContext
    val viewModel = viewModel<RegisterScreenViewModel>()
    // следим, прошла ли регисрация успешно
    val isRegisteredSuccess by viewModel.isRegisteredSuccessed.observeAsState()
    // безопасный запуска корутины
    LaunchedEffect(key1 = isRegisteredSuccess) {
        // Показывать тост при изменении состояния регистрации
        isRegisteredSuccess?.let {
            showToast(
                context = context,
                isSuccesedRegister = it
            )
            // в случае удачной регистрации переходим на главный экран
            if (isRegisteredSuccess == OK)
                onNavigateToMainScreen(Screen.Main)
        }
        // сбросить состояние регистрации
        viewModel.clearRegisteredState()
    }

    RegisterView(
        context = context,
        state = viewModel.state,
        onEvent = viewModel::onEvent,
        onRegister = viewModel::onRegister,
        onNavigateTo = onNavigateTo,
    )
}


@Composable
fun RegisterView(
    context: Context,
    state: RegisterScreenState = RegisterScreenState(),
    onEvent: (RegisterScreenEvent) -> Unit = {},
    onRegister: () -> Unit = {},
    onNavigateTo: (Screen) -> Unit = {},
) {
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
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
        OutlinedTextField(
            modifier = Modifier
                .padding(
                    top = 200.dp
                ),
            enabled = false,
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
            placeholder = {
                Text(
                    text = stringResource(R.string.enter_phone),
                    fontFamily = FontFamily(Font(DeviceFontFamilyName("sans-serif"))),
                )
            },
            shape = RoundedCornerShape(size = 15.dp)
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
                    top = 50.dp
                ),
            placeholder = {
                Text(
                    text = stringResource(R.string.enter_name),
                    fontFamily = FontFamily(Font(DeviceFontFamilyName("sans-serif"))),
                )
            },
            shape = RoundedCornerShape(size = 15.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
            ),
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
                    top = 50.dp
                ),
            placeholder = {
                Text(
                    text = stringResource(R.string.enter_username),
                    fontFamily = FontFamily(Font(DeviceFontFamilyName("sans-serif"))),
                )
            },
            shape = RoundedCornerShape(size = 15.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
            ),
        )
        StyledButton(
            modifier = Modifier
                .padding(
                    top = 50.dp
                )
                .width(280.dp)
                .height(50.dp),
            onClick = {
                //  валиден ли ник
                if (isValidUsername(state.username)) {
                    onRegister()
                }
                else {
                    showError = true
                    Toast.makeText(context, "Невалидный ник пользователя", Toast.LENGTH_SHORT).show()
                }
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
    }
}

private fun isValidUsername(username: String): Boolean {
    return username.matches(Regex("[A-Za-z0-9_-]+"))
}


fun showToast(
    context: Context,
    isSuccesedRegister: Int
) {
    when (isSuccesedRegister) {
        // необрабатываемый экземпляр
        UNPROCCESSABLE_ENTITY -> {
            Toast.makeText(
                context,
                context.getString(R.string.unproccessable_entity),
                Toast.LENGTH_LONG
            ).show()
        }
        // успешный успех
        Interactor.NOT_EXIST_USER-> {
            Toast.makeText(
                context,
                context.getString(R.string.successed_register),
                Toast.LENGTH_LONG
            ).show()
            //  если удачно - переходим на главный экран
        }

        Interactor.EXIST_USER ->
            Toast.makeText(
                context,
                context.getString(R.string.user_exist),
                Toast.LENGTH_LONG
            ).show()
        // данные введены неверно
        Interactor.UNKNOWN_ERROR -> Toast.makeText(
            context,
            context.getString(R.string.error_data_format),
            Toast.LENGTH_LONG
        ).show()

        else ->
            Toast.makeText(
                context,
                context.getString(R.string.unknown_error),
                Toast.LENGTH_LONG
            )
    }


}
