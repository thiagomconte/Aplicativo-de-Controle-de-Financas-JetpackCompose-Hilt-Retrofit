package com.example.financeapp.features.finance.addFinance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.repository.FinanceRepository
import com.example.financeapp.utils.Constants
import com.example.financeapp.utils.ResponseState
import com.example.financeapp.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFinanceViewModel @Inject constructor(
    private val repo: FinanceRepository
) : ViewModel() {

    private val _channel = Channel<UiEvent>()
    val channel = _channel.receiveAsFlow()

    private val _financeState: MutableStateFlow<ResponseState<String>> =
        MutableStateFlow(ResponseState.New)
    val financeState: StateFlow<ResponseState<String>> get() = _financeState

    fun onEvent(event: AddFinanceEvent) {
        if (event is AddFinanceEvent.AddFinance) {
            viewModelScope.launch {
                repo.newFinance(event.finance).onStart {
                    _financeState.value = ResponseState.Loading
                }.collectLatest {
                    when (it) {
                        is ResponseState.Success -> {
                            _financeState.value = it
                            sendEvent(UiEvent.PopBackStack)
                        }
                        else -> _financeState.value = it
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
