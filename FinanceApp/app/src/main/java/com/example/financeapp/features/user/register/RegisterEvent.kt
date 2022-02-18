package com.example.financeapp.features.user.register

import com.example.financeapp.models.User

sealed class RegisterEvent {
    data class RegisterUser(val user: User) : RegisterEvent()
}
