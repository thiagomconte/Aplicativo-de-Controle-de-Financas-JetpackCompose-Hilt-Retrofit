package com.example.financeapp.features.user.login

sealed class LoginEvent {
    data class Login(val email: String, val password: String) : LoginEvent()
}
