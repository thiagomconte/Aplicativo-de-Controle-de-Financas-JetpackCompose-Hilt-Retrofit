package com.example.financeapp.features.finance.dateReferenceList

sealed class DateReferenceEvent {
    data class OnItemClick(val id: String, val title: String) : DateReferenceEvent()
    object OnNewFinance : DateReferenceEvent()
}
