package com.example.financeapp.models

import com.google.gson.annotations.SerializedName

data class DateReference(
    @SerializedName("_id")
    val id: String,
    @SerializedName("month")
    val month: Int,
    @SerializedName("year")
    val year: Int,
    @SerializedName("value")
    val value: Float
)
