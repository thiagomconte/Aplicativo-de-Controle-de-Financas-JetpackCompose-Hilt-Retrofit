package com.example.financeapp.features.finance.dateReferenceList.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.financeapp.features.finance.dateReferenceList.DateReferenceEvent
import com.example.financeapp.models.DateReference
import com.example.financeapp.ui.theme.DebtRed
import com.example.financeapp.ui.theme.ProfitGreen
import com.example.financeapp.ui.theme.RobotoSerifRegular
import com.example.financeapp.utils.Common.Companion.formatNumber
import com.example.financeapp.utils.Maps

@Composable
fun DateReferenceItem(onItemClick: (DateReferenceEvent.OnItemClick) -> Unit, item: DateReference) {
    Box(
        modifier = Modifier
            .clickable {
                onItemClick(
                    DateReferenceEvent.OnItemClick(
                        item.id,
                        "${Maps.months[item.month] ?: ""}/${item.year}"
                    )
                )
            }
            .fillMaxWidth()
            .padding(12.dp),
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(
                "${Maps.months[item.month] ?: ""}/${item.year}",
                color = Color.Gray,
                fontFamily = RobotoSerifRegular
            )
            Text(
                formatNumber(item.value),
                color = if (item.value > 0) ProfitGreen else DebtRed,
                fontFamily = RobotoSerifRegular
            )
        }
    }
}
