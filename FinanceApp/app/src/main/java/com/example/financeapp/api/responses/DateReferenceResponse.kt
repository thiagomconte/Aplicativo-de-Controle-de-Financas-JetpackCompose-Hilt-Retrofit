package com.example.financeapp.api.responses

import com.example.financeapp.models.DateReference
import com.google.gson.annotations.SerializedName

data class DateReferenceResponse(
    @SerializedName("data")
    val data: List<DateReference>
)
