package com.example.financeapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.financeapp.R

val RobotoSerifRegular = FontFamily(
    Font(R.font.robotoserif_regular),
)

val RobotoSerifBlack = FontFamily(
    Font(R.font.robotoserif_black, FontWeight.Normal),
)

val RobotoSerifBold = FontFamily(
    Font(R.font.robotoserif_bold, FontWeight.Bold),
)

val RobotoSerifLight = FontFamily(
    Font(R.font.robotoseriflight, FontWeight.Light)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h4 = TextStyle(
        fontFamily = RobotoSerifBlack,
        fontSize = 34.sp,
        fontWeight = FontWeight.Normal
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)
