package com.example.financeapp.utils.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AlertDialogComponent(text: String, onDismiss: () -> Unit) {
    AlertDialog(onDismissRequest = { onDismiss() }, confirmButton = {
        Button(
            onClick = { onDismiss() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Text(text = "Fechar")
        }
    }, text = {
        Text(text)
    }, title = { Text("Erro") })
}
