package com.example.financeapp.features.user.login.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.financeapp.R
import com.example.financeapp.features.user.login.LoginEvent
import com.example.financeapp.features.user.login.LoginViewModel
import com.example.financeapp.ui.theme.RobotoSerifLight
import com.example.financeapp.utils.Common.Companion.isEmailValid
import com.example.financeapp.utils.Constants
import com.example.financeapp.utils.ResponseState
import com.example.financeapp.utils.UiEvent
import com.example.financeapp.utils.components.AlertDialogComponent
import com.example.financeapp.utils.components.ErrorText
import com.example.financeapp.utils.components.LoadingComponent
import com.example.financeapp.utils.components.RobotoTextRegular
import kotlinx.coroutines.flow.collect

@Composable
fun LoginScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val email = remember { mutableStateOf(TextFieldValue("")) }
    val password = remember { mutableStateOf(TextFieldValue("")) }
    val passwordVisibility = remember { mutableStateOf(false) }
    val emailError = remember { mutableStateOf(false) }
    val passwordError = remember { mutableStateOf(false) }
    val showMsgError = remember { mutableStateOf(false) }
    val loading = remember { mutableStateOf(false) }
    val msgError = remember { mutableStateOf("") }
    val loginState = viewModel.state.collectAsState(ResponseState.New).value

    LaunchedEffect(Unit) {
        viewModel.channel.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(UiEvent.Navigate(event.route))
                is UiEvent.ShowAlertDialog -> {
                    showMsgError.value = true
                    msgError.value = event.msg
                }
                else -> Unit
            }
        }
    }

    if (loginState is ResponseState.Error) {
        loading.value = false
    }

    if (showMsgError.value) {
        AlertDialogComponent(msgError.value) {
            showMsgError.value = false
        }
    }

    if (loading.value) {
        LoadingComponent()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Account icon",
            modifier = Modifier
                .size(200.dp)
                .align(CenterHorizontally)
        )
//        Text(
//            stringResource(id = R.string.login),
//            style = MaterialTheme.typography.h4,
//            fontFamily = FontFamily.Serif,
//            color = Color.White,
//            textAlign = TextAlign.Center,
//            modifier = Modifier
//                .fillMaxWidth()
//                .border(
//                    width = 2.dp,
//                    shape = RoundedCornerShape(8.dp),
//                    color = Color.Black
//                )
//                .background(color = Color.Black)
//                .padding(vertical = 8.dp, horizontal = 32.dp)
//                .align(CenterHorizontally),
//        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            label = {
                RobotoTextRegular(text = stringResource(id = R.string.email))
            },
            shape = RoundedCornerShape(20.dp),
            value = email.value,
            onValueChange = {
                email.value = it
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                unfocusedLabelColor = Color.Black,
                focusedLabelColor = Color.Black,
                cursorColor = Color.Black,
                focusedIndicatorColor = if (emailError.value) Color.Red else Color.Black,
                unfocusedIndicatorColor = if (emailError.value) Color.Red else Color.Black,
                textColor = Color.Black,
            ),
            textStyle = TextStyle(fontFamily = RobotoSerifLight)
        )
        if (emailError.value) {
            ErrorText(text = stringResource(id = R.string.invalid_email))
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            label = {
                RobotoTextRegular(text = stringResource(id = R.string.password))
            },
            shape = RoundedCornerShape(20.dp),
            value = password.value,
            onValueChange = {
                password.value = it
            },
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility.value = !passwordVisibility.value
                }) {
                    Icon(
                        painter =
                        if (passwordVisibility.value)
                            painterResource(id = R.drawable.ic_visibility_black_24dp)
                        else painterResource(
                            id = R.drawable.ic_visibility_off_black_24dp
                        ),
                        contentDescription = ""
                    )
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                unfocusedLabelColor = Color.Black,
                focusedLabelColor = Color.Black,
                cursorColor = Color.Black,
                focusedIndicatorColor = if (passwordError.value) Color.Red else Color.Black,
                unfocusedIndicatorColor = if (passwordError.value) Color.Red else Color.Black,
                textColor = Color.Black,
            ),
            textStyle = TextStyle(fontFamily = RobotoSerifLight)
        )
        if (passwordError.value) {
            ErrorText(text = stringResource(id = R.string.inform_password))
        }
        Button(
            onClick = {
                validate(email.value.text, password.value.text, onEmailError = {
                    emailError.value = it
                }, onPasswordError = {
                    passwordError.value = it
                }, onValidate = {
                    viewModel.onEvent(
                        LoginEvent.Login(
                            email.value.text,
                            password.value.text
                        )
                    )
                    loading.value = true
                })
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black, contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(id = R.string.enter),
                modifier = Modifier.padding(vertical = 4.dp),
                style = MaterialTheme.typography.h6
            )
        }
        OutlinedButton(
            onClick = { onNavigate(UiEvent.Navigate(Constants.Routes.REGISTER)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(32.dp),
            border = BorderStroke(2.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White, contentColor = Color.Black
            )
        ) {
            Text(
                text = stringResource(id = R.string.create_account),
                modifier = Modifier.padding(vertical = 4.dp),
                style = MaterialTheme.typography.h6
            )
        }
    }
}

fun validate(
    email: String,
    password: String,
    onEmailError: (Boolean) -> Unit,
    onPasswordError: (Boolean) -> Unit,
    onValidate: () -> Unit
) {
    onEmailError(!isEmailValid(email))
    onPasswordError(password.isBlank())
    if (email.isNotBlank() && password.isNotBlank()) {
        onValidate()
    }
}
