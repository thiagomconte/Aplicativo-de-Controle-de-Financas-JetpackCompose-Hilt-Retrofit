package com.example.financeapp.features.finance.addFinance

import com.example.financeapp.models.Finance

sealed class AddFinanceEvent {
    data class AddFinance(val finance: Finance) : AddFinanceEvent()
}
