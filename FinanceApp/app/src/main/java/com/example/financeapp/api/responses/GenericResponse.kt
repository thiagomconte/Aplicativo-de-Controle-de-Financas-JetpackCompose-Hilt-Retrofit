package com.example.financeapp.api.responses

import com.google.gson.annotations.SerializedName

data class GenericResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String
)
