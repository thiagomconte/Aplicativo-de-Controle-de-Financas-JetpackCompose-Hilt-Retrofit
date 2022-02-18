package com.example.financeapp.features.user.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.repository.UserRepository
import com.example.financeapp.utils.ResponseState
import com.example.financeapp.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repo: UserRepository
) : ViewModel() {

    private val _channel = Channel<UiEvent>()
    val channel = _channel.receiveAsFlow()

    private val _state: MutableStateFlow<ResponseState<String>> =
        MutableStateFlow(ResponseState.Loading)
    val state: StateFlow<ResponseState<String>> get() = _state

    fun onEvent(event: RegisterEvent) {
        if (event is RegisterEvent.RegisterUser) {
            viewModelScope.launch {
                repo.register(event.user).onStart {
                    _state.value = ResponseState.Loading
                }.collectLatest {
                    when (it) {
                        is ResponseState.Success -> sendEvent(UiEvent.PopBackStack)
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
