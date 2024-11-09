package com.mariiadeveloper.unicornmessenger.presentation.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
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
import com.joelkanyi.jcomposecountrycodepicker.component.rememberKomposeCountryCodePickerState
import com.mariiadeveloper.unicornmessenger.R
import com.mariiadeveloper.unicornmessenger.app.App
import com.mariiadeveloper.unicornmessenger.domain.Interactor
import com.mariiadeveloper.unicornmessenger.domain.Interactor.STATUS_CODE.EMPTY_PHONE
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
import com.mariiadeveloper.unicornmessenger.presentation.screen.state.CodeScreenEvent
import com.mariiadeveloper.unicornmessenger.presentation.ui.theme.BoxFilterColor
import com.mariiadeveloper.unicornmessenger.presentation.ui.theme.ButtonFilterColor

@Composable
fun RegisterScreen(


    // чтобы не передавать нав хост контроллер, тк могут быть утечки памяти6
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
        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            modifier = Modifier
                .padding(

                ),
            enabled = false,
            value = state.phone,
            onValueChange = {
                onEvent(RegisterScreenEvent.PhoneUpdate(it))
            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 25.sp,
                //fontWeight = FontWeight.Bold

            ),
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
                    text = stringResource(R.string.enter_code),
                    fontFamily = FontFamily(Font(DeviceFontFamilyName("sans-serif"))),
                    color =  MaterialTheme.colorScheme.outline
                )
            },
            shape = RoundedCornerShape(size = 15.dp),
            colors = TextFieldDefaults.colors(
                disabledContainerColor = ButtonFilterColor,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor =  Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,


                ),
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .padding(

                ),
            value = state.name,
            onValueChange = {
                onEvent(RegisterScreenEvent.NameUpdate(it))
            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            leadingIcon = {
                Icon(
                    painter = rememberVectorPainter(
                        image = Icons.Outlined.Person
                    ),
                    contentDescription = null
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.enter_name),
                    fontFamily = FontFamily(Font(DeviceFontFamilyName("sans-serif"))),
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
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .padding(

                ),
            value = state.username,
            onValueChange = {
                onEvent(RegisterScreenEvent.UsernameUpdated(it))
            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            leadingIcon = {
                Icon(
                    painter = rememberVectorPainter(
                        image = Icons.Outlined.Face
                    ),
                    contentDescription = null
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.enter_username),
                    fontFamily = FontFamily(Font(DeviceFontFamilyName("sans-serif"))),
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
        Spacer(modifier = Modifier.height(50.dp))
        StyledButton(
            modifier = Modifier
                .padding(
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
                    fontSize = 25.sp,
                    fontFamily = FontFamily(Font(DeviceFontFamilyName("sans-serif"))),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
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
