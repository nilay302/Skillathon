package com.example.skillathon.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skillathon.R

@Composable
fun CustomButton(text: String, onClick: () -> Unit){
    Button(onClick = onClick,
        modifier= Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 36.dp,
            ),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.blue_color_theme),
        )
    ) {
        Text(text = text,
            color = Color.White,
            fontSize = 20.sp, modifier = Modifier.padding(vertical = 4.dp))
    }
}