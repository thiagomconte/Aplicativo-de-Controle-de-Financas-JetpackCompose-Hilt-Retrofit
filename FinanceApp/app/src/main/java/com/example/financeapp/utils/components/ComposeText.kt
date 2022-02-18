package com.example.financeapp.utils.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financeapp.ui.theme.RobotoSerifBlack
import com.example.financeapp.ui.theme.RobotoSerifBold
import com.example.financeapp.ui.theme.RobotoSerifRegular

@Composable
fun RobotoTextBlack(text: String) {
    Text(text, fontFamily = RobotoSerifBlack)
}

@Composable
fun RobotoTextBold(text: String) {
    Text(text, fontFamily = RobotoSerifBold)
}

@Composable
fun RobotoTextRegular(text: String) {
    Text(text, fontFamily = RobotoSerifRegular)
}

@Composable
fun ErrorText(text: String) {
    Text(
        text,
        fontFamily = RobotoSerifRegular,
        fontSize = 12.sp,
        modifier = Modifier.padding(horizontal = 8.dp),
        color = Color.Red,
    )
}
