package com.example.financeapp.features.finance.financeDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.models.Finance
import com.example.financeapp.repository.FinanceRepository
import com.example.financeapp.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinanceViewModel @Inject constructor(
    private val repo: FinanceRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _financeState: MutableStateFlow<ResponseState<List<Finance>>> =
        MutableStateFlow(ResponseState.Loading)
    val financeState: StateFlow<ResponseState<List<Finance>>> get() = _financeState

    var id: String = savedStateHandle.get<String>("id").toString()
    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            getFinances(id)
            _isRefreshing.emit(false)
        }
    }

    init {
        getFinances(id)
    }

    private fun getFinances(id: String) {
        if (id.isNotBlank()) {
            viewModelScope.launch {
                repo.getFinanceByDateReference(id).onStart {
                    _financeState.value = ResponseState.Loading
                }.collectLatest {
                    when (it) {
                        is ResponseState.Success -> {
                            if (it.data.isEmpty()) {
                                _financeState.value = ResponseState.Empty
                            } else {
                                _financeState.value = it
                            }
                        }
                        else -> _financeState.value = it
                    }
                }
            }
        }
    }
}
