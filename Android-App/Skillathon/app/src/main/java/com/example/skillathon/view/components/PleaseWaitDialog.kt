package com.example.skillathon.view.components
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun PleaseWaitDialog() {
    AlertDialog(
        modifier = Modifier.padding(32.dp).height(64.dp),
        onDismissRequest = { /* Do nothing */ },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.width(16.dp))
                Text(text = "Please wait...")
            }
        },
        buttons = {},
    )
}