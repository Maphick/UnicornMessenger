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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.TableInfo

import com.mariiadeveloper.unicornmessenger.R
import com.mariiadeveloper.unicornmessenger.presentation.navigation.Screen
import com.mariiadeveloper.unicornmessenger.presentation.screen.viewmodel.LoginScreenViewModel
import com.mariiadeveloper.unicornmessenger.presentation.ui.component.StyledButton
import com.mariiadeveloper.unicornmessenger.presentation.ui.theme.UnicornMessengerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    // чтобы не передавать нав хост контроллер, тк могут быть утечки памяти
    viewModel: LoginScreenViewModel = viewModel(),
    onNavigateTo: (Screen) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
               // start = 50.dp,
               // end = 50.dp
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
            value = viewModel.email,
            onValueChange = viewModel::updateEmail,
            leadingIcon = {
                Icon(
                    painter = rememberVectorPainter(
                        image = Icons.Outlined.Email
                    ),
                    contentDescription = null
                )
            },
            // по умолчанию
            placeholder = {
                Text(
                    text = stringResource(R.string.enter_email)
                )
            }
        )
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = viewModel::updatePassword,
            leadingIcon = {
                Icon(
                    painter = rememberVectorPainter(
                        image = Icons.Outlined.Lock
                    ),
                    contentDescription = null
                )
            },
            modifier = Modifier
                .padding(
                    top = 10.dp
                ),
            visualTransformation = PasswordVisualTransformation(),
            placeholder = {
                Text(
                    text = stringResource(R.string.enter_password)
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
            onClick = {},
            content =
            {
                Text(
                    text = stringResource(R.string.login),
                    fontSize = 19.sp,
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
            }
        )
        Text(
            text = stringResource(R.string.no_account_register),
            fontSize = 16.sp,
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
    LoginScreen()
}

@Preview(showBackground = true)
@Composable
private fun  LoginScreenPreviewLight()
{
    UnicornMessengerTheme(darkTheme = false) {
        LoginScreen()
    }

}

@Preview(showBackground = false)
@Composable
private fun  LoginScreenPreviewwDark()
{
    UnicornMessengerTheme(darkTheme = true)  {
        LoginScreen()
    }
}