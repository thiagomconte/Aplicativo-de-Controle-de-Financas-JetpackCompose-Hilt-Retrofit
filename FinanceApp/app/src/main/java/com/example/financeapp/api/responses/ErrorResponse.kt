package com.example.financeapp.api.responses

import com.google.gson.annotations.SerializedName

class ErrorResponse(
    @SerializedName("error")
    val error: String
)
