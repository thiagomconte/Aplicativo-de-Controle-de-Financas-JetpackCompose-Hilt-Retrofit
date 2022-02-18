package com.example.financeapp.api.responses

import com.example.financeapp.models.Finance
import com.google.gson.annotations.SerializedName

data class FinanceResponse(
    @SerializedName("data")
    val data: List<Finance>
)
