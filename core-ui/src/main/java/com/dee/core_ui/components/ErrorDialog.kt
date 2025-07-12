package com.dee.core_ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable


data class ErrorDialogModel(
    val title: String,
    val message: String,
    val buttonText: String,
    val onButtonClicked: () -> Unit = {}
)

@Composable
fun ErrorDialog(
    model: ErrorDialogModel,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(model.title) },
        text = { Text(model.message) },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(model.buttonText)
            }
        }
    )
}