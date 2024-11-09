package com.mariiadeveloper.unicornmessenger.presentation.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.DeviceFontFamilyName
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.joelkanyi.jcomposecountrycodepicker.component.KomposeCountryCodePicker
import com.joelkanyi.jcomposecountrycodepicker.component.rememberKomposeCountryCodePickerState

import com.mariiadeveloper.unicornmessenger.R
import com.mariiadeveloper.unicornmessenger.app.App
import com.mariiadeveloper.unicornmessenger.domain.Interactor.STATUS_CODE.CONFIRM_CODE_ERROR
import com.mariiadeveloper.unicornmessenger.domain.Interactor.STATUS_CODE.EMPTY_PHONE
import com.mariiadeveloper.unicornmessenger.domain.Interactor.STATUS_CODE.EXIST_USER
import com.mariiadeveloper.unicornmessenger.domain.Interactor.STATUS_CODE.NETWORK_ERROR
import com.mariiadeveloper.unicornmessenger.domain.Interactor.STATUS_CODE.NOT_EXIST_USER
import com.mariiadeveloper.unicornmessenger.domain.Interactor.STATUS_CODE.OK
import com.mariiadeveloper.unicornmessenger.domain.Interactor.STATUS_CODE.UN_AUTHORIZED
import com.mariiadeveloper.unicornmessenger.presentation.navigation.Screen
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.LoginScreenEvent
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.LoginScreenState
import com.mariiadeveloper.unicornmessenger.presentation.screen.viewmodel.LoginScreenViewModel
import com.mariiadeveloper.unicornmessenger.presentation.ui.component.StyledButton
import com.mariiadeveloper.unicornmessenger.presentation.ui.theme.BoxFilterColor

//Инициализируем интерактор
//private var interactor: Interactor = App.instance.interactor

// контейнер, в котором хранится вью модель и стейт
@Composable
fun LoginScreen(


    // чтобы не передавать нав хост контроллер, тк могут быть утечки памяти
    onNavigateTo: (Screen) -> Unit = {},
    // переход на главный экран
    onNavigateToCodeScreen: (Screen) -> Unit = {}
) {
    val context = App.instance.appContext
    val viewModel = viewModel<LoginScreenViewModel>()
    // следим, прошла ли регисрация успешно
    val isLoginSuccess by viewModel.isAuthSuccessed.observeAsState()
    // безопасный запуска корутины
    LaunchedEffect(key1 = isLoginSuccess) {
        // Показывать тост при изменении состояния регистрации
        isLoginSuccess.let {
            if ((it != null)&&(it!=0)){
                showAuthToast(
                    context = context,
                    isLoginSuccess = it
                )
            }
            // в случае удачной аутентификации переходим на экран ввода кода
            if (isLoginSuccess == OK)
                onNavigateToCodeScreen(Screen.Code)
        }
        // сбросить состояние аутентификации
        viewModel.clearRegisteredState()
    }

    LoginView(
        context = context,
        state = viewModel.state,
        // референс на функцию onEvent
        //  т.е. она автоматически будет вызываться с параметром, который будет туда передаваться
        onEvent = viewModel::onEvent,
        onAuth = viewModel::onAuth,
        onNavigateTo = onNavigateTo
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    context: Context,
    // чтобы не передавать нав хост контроллер, тк могут быть утечки памяти
    //viewModel: LoginScreenViewModel = viewModel(),
    state: LoginScreenState = LoginScreenState(),
    // lambda-функция для изменения стейта
    onEvent: (LoginScreenEvent) -> Unit = {},
    // по нажатию на кнопку отправка кода
    onAuth: () -> Unit = {},
    confirmCodeScreen: (Screen) -> Unit = {},
            onNavigateTo: (Screen) -> Unit = {}
) {
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
            )
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Spacer(modifier = Modifier.height(10.dp))
        Image(
            modifier = Modifier.size(150.dp),
            painter = painterResource(R.drawable.unicorn_0),
            contentDescription = "Logo",
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier
                .padding(),
            text = stringResource(R.string.app_name),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(DeviceFontFamilyName("sans-serif"))),
            color = MaterialTheme.colorScheme.onBackground
        )

        val countryState = rememberKomposeCountryCodePickerState(
            showCountryCode = true,
            showCountryFlag = true,
            defaultCountryCode = "RU",
        )
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            modifier = Modifier
                .padding(

                ),
            value = state.phone,
            onValueChange = {
                onEvent(LoginScreenEvent.PhoneUpdated(it))
            },
            leadingIcon = {
                KomposeCountryCodePicker(
                    modifier = Modifier,
                    showOnlyCountryCodePicker = true,
                    text = state.code,
                    textStyle = TextStyle(
                        // цвет кода страны
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    state = countryState,
                    onValueChange = {
                        onEvent(LoginScreenEvent.CodeUpdated(it))
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Green,
                        focusedContainerColor = Color.Green,
                        focusedTextColor = Color.Red,
                        unfocusedTextColor = Color.Green,
                    ),
                )
            },
            // по умолчанию
            placeholder = {
                Text(
                    text = stringResource(R.string.enter_phone),
                    fontFamily = FontFamily(Font(DeviceFontFamilyName("sans-serif"))),
                    // цвет подсказки плейсхолдера
                    color =  MaterialTheme.colorScheme.outline
                )
            },
            shape = RoundedCornerShape(size = 15.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor =  Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,

            ),
        )
        Spacer(modifier = Modifier.height(100.dp))
        StyledButton(
            modifier = Modifier
                .padding(
                )
                .width(280.dp)
                .height(50.dp),
            onClick = {
                if (state.phone != "")
                    onAuth()
                else
                {

                        showAuthToast(
                            context = context,
                            isLoginSuccess = EMPTY_PHONE
                        )

                }
            },
            content =
            {
                Text(
                    text = stringResource(R.string.get_code),
                    fontSize = 25.sp,
                    fontFamily = FontFamily(Font(DeviceFontFamilyName("sans-serif"))),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        )
    }

}


fun showAuthToast(
    context: Context,
    isLoginSuccess: Int
) {
    when (isLoginSuccess) {
        // успешный успех
        OK -> {
            Toast.makeText(
                context,
                context.getString(R.string.successed_auth),
                Toast.LENGTH_LONG
            ).show()
            //  если удачно - переходим на экран подтверждения кода
        }
       EMPTY_PHONE  ->
           Toast.makeText(
               context,
               context.getString(R.string.empty_phone),
               Toast.LENGTH_LONG
           ).show()

       EXIST_USER ->
            Toast.makeText(
                context,
                context.getString(R.string.user_exist),
                Toast.LENGTH_LONG
            ).show()

        NOT_EXIST_USER -> {
            Toast.makeText(
                context,
                context.getString(R.string.no_account_register),
                Toast.LENGTH_LONG
            ).show()
        }
        // данные введены неверно
        NETWORK_ERROR -> Toast.makeText(
            context,
            context.getString(R.string.error_auth),
            Toast.LENGTH_LONG
        ).show()

        // пользователь не авторизирован
        UN_AUTHORIZED -> {

        }
        // неверный код подтверждения
        CONFIRM_CODE_ERROR -> {
            Toast.makeText(
                context,
                context.getString(R.string.confirm_code_error),
                Toast.LENGTH_LONG
            ).show()
        }

        else -> {
            Toast.makeText(
                context,
                 "GETTING_AUTH_CODE_ERROR/n Произошла ошибка /n" + isLoginSuccess,
                Toast.LENGTH_LONG
            )
        }
    }
}
