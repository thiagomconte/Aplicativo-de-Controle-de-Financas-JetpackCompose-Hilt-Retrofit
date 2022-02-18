package com.example.financeapp.utils

sealed class UiEvent {
    object PopBackStack : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    data class ShowAlertDialog(val msg: String) : UiEvent()
}
