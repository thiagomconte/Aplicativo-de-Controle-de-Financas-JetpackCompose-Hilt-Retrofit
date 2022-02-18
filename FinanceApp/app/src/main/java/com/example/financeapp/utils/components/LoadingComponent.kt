package com.example.financeapp.utils.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex

@Composable
fun LoadingComponent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.3f))
            .zIndex(1f)
    ) {
        CircularProgressIndicator(
            color = Color.Black,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}
