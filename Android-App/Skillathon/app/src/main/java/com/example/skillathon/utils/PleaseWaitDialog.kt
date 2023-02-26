package com.example.skillathon.utils
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PleaseWaitDialog(isDisplayed: Boolean) {
    var isOpen by remember { mutableStateOf(isDisplayed) }
    if (isOpen) {
        AlertDialog(
            onDismissRequest = { isOpen = false },
            title = { Text(text = "Please wait...") },
            buttons = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}