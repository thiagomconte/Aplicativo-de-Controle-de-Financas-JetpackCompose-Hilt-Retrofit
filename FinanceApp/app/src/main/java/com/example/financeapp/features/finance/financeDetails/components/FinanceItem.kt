package com.example.financeapp.features.finance.financeDetails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.financeapp.models.Finance
import com.example.financeapp.ui.theme.DebtRed
import com.example.financeapp.ui.theme.ProfitGreen
import com.example.financeapp.ui.theme.RobotoSerifRegular
import com.example.financeapp.utils.Common.Companion.formatNumber
import com.example.financeapp.utils.Common.Companion.stringToDate
import com.example.financeapp.utils.Constants

@Composable
fun FinanceItem(item: Finance) {
    Box(
        modifier = Modifier
            .clickable {
            }
            .fillMaxWidth()
            .padding(12.dp),
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(
                "${stringToDate(item.date)} - ${item.description}",
                color = Color.Gray,
                maxLines = 1,
                fontFamily = RobotoSerifRegular,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                formatNumber(item.value),
                fontFamily = RobotoSerifRegular,
                color = if (item.type == Constants.PROFIT) ProfitGreen else DebtRed
            )
        }
    }
}
