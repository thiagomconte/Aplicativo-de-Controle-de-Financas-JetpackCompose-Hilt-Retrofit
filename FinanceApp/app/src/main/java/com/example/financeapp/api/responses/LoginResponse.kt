package com.example.financeapp.api.responses

import com.example.financeapp.models.User
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("user")
    val user: User,
    @SerializedName("token")
    val token: String
)
