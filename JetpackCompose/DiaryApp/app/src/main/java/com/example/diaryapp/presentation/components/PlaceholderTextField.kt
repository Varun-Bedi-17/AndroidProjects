package com.example.diaryapp.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PlaceholderTextField(
    text: String,
    onTextChange: (String) -> Unit,
    placeholder: String,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    var isPlaceholderVisible by remember { mutableStateOf(text.isEmpty()) }
    Box {
        BasicTextField(
            value = text,
            onValueChange = {
                onTextChange(it)
                isPlaceholderVisible = it.isEmpty()
            },
            textStyle = TextStyle(color = Color.White),
            singleLine = true,
            visualTransformation = visualTransformation,
            cursorBrush = SolidColor(Color.White)
        )
        if (isPlaceholderVisible) {
            Text(
                text = placeholder,
                color = Color.DarkGray,
                modifier = Modifier.padding(start = 3.dp)
            )
        }
    }
}

@Preview
@Composable
fun ShowPreview2(){
    PlaceholderTextField(
        text = "",
        onTextChange = {  },
        placeholder = "Email ID"
    )
}