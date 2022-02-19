package com.example.financeapp.features.user.register.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import com.example.financeapp.features.user.register.RegisterEvent
import com.example.financeapp.features.user.register.RegisterViewModel
import com.example.financeapp.models.User
import com.example.financeapp.ui.theme.RobotoSerifRegular
import com.example.financeapp.utils.Common.Companion.isEmailValid
import com.example.financeapp.utils.ResponseState
import com.example.financeapp.utils.UiEvent
import com.example.financeapp.utils.components.AlertDialogComponent
import com.example.financeapp.utils.components.ErrorText
import com.example.financeapp.utils.components.LoadingComponent
import com.example.financeapp.utils.components.RobotoTextRegular
import kotlinx.coroutines.flow.collect

@Composable
fun RegisterScreen(
    onPopBackStack: (UiEvent.PopBackStack) -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val name = remember { mutableStateOf(TextFieldValue("")) }
    val email = remember { mutableStateOf(TextFieldValue("")) }
    val password = remember { mutableStateOf(TextFieldValue("")) }
    val loading = remember { mutableStateOf(false) }
    val passwordVisibility = remember { mutableStateOf(false) }
    val nameError = remember { mutableStateOf(false) }
    val emailError = remember { mutableStateOf(false) }
    val passwordError = remember { mutableStateOf(false) }
    val showMsgError = remember { mutableStateOf(false) }
    val msgError = remember { mutableStateOf("") }
    val registerState = viewModel.state.collectAsState(ResponseState.Loading).value

    LaunchedEffect(Unit) {
        viewModel.channel.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onPopBackStack(UiEvent.PopBackStack)
                is UiEvent.ShowAlertDialog -> {
                    showMsgError.value = true
                    msgError.value = event.msg
                }
                else -> Unit
            }
        }
    }
    if (registerState is ResponseState.Error) {
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_person_add_black_48dp),
            contentDescription = "Account icon",
            modifier = Modifier
                .size(200.dp)
                .align(CenterHorizontally)
        )
//        Text(
//            stringResource(id = R.string.register),
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = {
                RobotoTextRegular(text = stringResource(id = R.string.name))
            },
            shape = RoundedCornerShape(20.dp),
            value = name.value,
            onValueChange = {
                name.value = it
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                unfocusedLabelColor = Color.Black,
                focusedLabelColor = Color.Black,
                cursorColor = Color.Black,
                focusedIndicatorColor = if (nameError.value) Color.Red else Color.Black,
                unfocusedIndicatorColor = if (nameError.value) Color.Red else Color.Black,
                textColor = Color.Black,
            ),
            textStyle = TextStyle(fontFamily = RobotoSerifRegular)
        )
        if (nameError.value) {
            ErrorText(text = stringResource(id = R.string.name_validator))
        }
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
            textStyle = TextStyle(fontFamily = RobotoSerifRegular)
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
            textStyle = TextStyle(fontFamily = RobotoSerifRegular)
        )
        if (passwordError.value) {
            ErrorText(text = stringResource(id = R.string.password_validator))
        }
        Button(
            onClick = {
                validate(email.value.text, password.value.text, name.value.text, onNameError = {
                    nameError.value = it
                }, onPasswordError = {
                    passwordError.value = it
                }, onEmailError = {
                    emailError.value = it
                }, onValidate = {
                    viewModel.onEvent(
                        RegisterEvent.RegisterUser(
                            User(
                                name.value.text,
                                email.value.text,
                                password.value.text
                            )
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
                text = stringResource(id = R.string.confirm_register),
                modifier = Modifier.padding(vertical = 4.dp),
                style = MaterialTheme.typography.h6
            )
        }
        OutlinedButton(
            onClick = { onPopBackStack(UiEvent.PopBackStack) },
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
                text = stringResource(id = R.string.back),
                modifier = Modifier.padding(vertical = 4.dp),
                style = MaterialTheme.typography.h6
            )
        }
    }
}

fun validate(
    email: String,
    password: String,
    name: String,
    onNameError: (Boolean) -> Unit,
    onEmailError: (Boolean) -> Unit,
    onPasswordError: (Boolean) -> Unit,
    onValidate: () -> Unit
) {
    onNameError(!isNameValid(name))
    onEmailError(!isEmailValid(email))
    onPasswordError(!isPasswordValid(password))
    if (isNameValid(name) && isPasswordValid(password) && isEmailValid(email)) {
        onValidate()
    }
}

private fun isNameValid(name: String) = name.length in 4..29

private fun isPasswordValid(password: String) = password.length in 9..19
