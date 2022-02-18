package com.example.financeapp.utils.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.financeapp.features.MainActivity
import com.example.financeapp.utils.Constants

@Composable
fun TopAppBarComponent() {
    val sharedPrefs = LocalContext.current.getSharedPreferences(Constants.USER_PREFERENCES, 0)
    TopAppBar(
        title = {
            Text(text = sharedPrefs.getString(Constants.NAME, "") ?: "")
        },
        actions = {
            Text(
                text = "Sair",
                modifier = Modifier
                    .clickable {
                        sharedPrefs
                            .edit()
                            .clear()
                            .apply()
                        MainActivity.reload()
                    }
                    .padding(horizontal = 8.dp)
            )
        },
        backgroundColor = Color.Black,
        contentColor = Color.White
    )
}
