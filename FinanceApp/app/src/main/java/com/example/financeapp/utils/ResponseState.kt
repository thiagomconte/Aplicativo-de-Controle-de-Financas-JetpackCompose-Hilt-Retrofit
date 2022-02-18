package com.example.financeapp.utils

sealed class ResponseState<out T> {
    object New : ResponseState<Nothing>()
    object Loading : ResponseState<Nothing>()
    object Empty : ResponseState<Nothing>()
    data class Error(val msg: String) : ResponseState<Nothing>()
    data class Success<out T>(val data: T) : ResponseState<T>()
}
