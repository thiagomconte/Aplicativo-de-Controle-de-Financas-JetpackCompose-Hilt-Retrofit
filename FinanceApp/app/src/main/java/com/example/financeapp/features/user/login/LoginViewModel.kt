package com.example.financeapp.features.user.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.api.TokenInterceptor
import com.example.financeapp.repository.UserRepository
import com.example.financeapp.utils.Constants
import com.example.financeapp.utils.ResponseState
import com.example.financeapp.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: UserRepository,
    private val context: Context,
) : ViewModel() {

    @Inject
    lateinit var tokenInterceptor: TokenInterceptor

    private val _channel = Channel<UiEvent>()
    val channel = _channel.receiveAsFlow()

    private val _state: MutableStateFlow<ResponseState<String>> =
        MutableStateFlow(ResponseState.New)
    val state: StateFlow<ResponseState<String>> get() = _state

    fun onEvent(event: LoginEvent) {
        if (event is LoginEvent.Login) {
            viewModelScope.launch {
                repo.login(event.email, event.password).onStart {
                    _state.value = ResponseState.Loading
                }.collectLatest {
                    when (it) {
                        is ResponseState.Success -> {
                            val sharedPref =
                                context.getSharedPreferences(Constants.USER_PREFERENCES, 0)
                            with(sharedPref.edit()) {
                                putString(Constants.TOKEN, it.data.token)
                                putString(Constants.EMAIL, it.data.user.email)
                                putString(Constants.NAME, it.data.user.name)
                                apply()
                            }
                            tokenInterceptor.token = it.data.token
                            sendEvent(UiEvent.Navigate(Constants.Routes.HOME))
                        }
                        is ResponseState.Error -> {
                            _state.value = it
                            sendEvent(UiEvent.ShowAlertDialog(it.msg))
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _channel.send(event)
        }
    }
}
