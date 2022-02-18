package com.example.financeapp.features.finance.dateReferenceList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.models.DateReference
import com.example.financeapp.repository.DateReferenceRepository
import com.example.financeapp.utils.Constants
import com.example.financeapp.utils.ResponseState
import com.example.financeapp.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DateReferenceViewModel @Inject constructor(
    private val repo: DateReferenceRepository,
    private val context: Context
) : ViewModel() {

    private val _channel = Channel<UiEvent>()
    val channel = _channel.receiveAsFlow()

    private val _dateReferencesState: MutableStateFlow<ResponseState<List<DateReference>>> =
        MutableStateFlow(ResponseState.Loading)
    val dateReferencesState: StateFlow<ResponseState<List<DateReference>>> get() = _dateReferencesState

    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            getDateReferences()
            _isRefreshing.emit(false)
        }
    }

    fun getDateReferences() {
        viewModelScope.launch {
            repo.getAllDateReferences().onStart {
                _dateReferencesState.value = ResponseState.Loading
            }.collectLatest {
                when (it) {
                    is ResponseState.Success -> {
                        if (it.data.isEmpty()) {
                            _dateReferencesState.value = ResponseState.Empty
                        } else {
                            _dateReferencesState.value = it
                        }
                    }
                    else -> _dateReferencesState.value = it
                }
            }
        }
    }

    fun onEvent(event: DateReferenceEvent) {
        when (event) {
            is DateReferenceEvent.OnItemClick -> sendEvent(UiEvent.Navigate("${Constants.Routes.FINANCES}?id=${event.id}&title=${event.title}"))
            is DateReferenceEvent.OnNewFinance -> sendEvent(UiEvent.Navigate(Constants.Routes.ADD_FINANCE))
        }
    }

    private fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _channel.send(event)
        }
    }
}
