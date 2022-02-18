package com.example.financeapp.utils

import android.util.Patterns
import java.text.NumberFormat
import java.util.*

class Common {
    companion object {
        fun formatNumber(value: Float): String {
            val numberFormat = NumberFormat.getCurrencyInstance()
            numberFormat.maximumFractionDigits = 2
            numberFormat.currency = Currency.getInstance(Locale("pt", "BR"))

            return numberFormat.format(value)
        }

        fun stringToDate(date: String): String {
            return date.split("T")[0].split("-")[2]
        }

        fun isEmailValid(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }


    }
}
