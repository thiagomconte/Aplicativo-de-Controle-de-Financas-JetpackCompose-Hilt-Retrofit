package com.example.financeapp.models

import com.google.gson.annotations.SerializedName

data class Finance(
    @SerializedName("_id")
    val id: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("value")
    val value: Float,
    @SerializedName("date")
    var date: String,
    @SerializedName("type")
    var type: String
)
