package com.example.diaryapp.presentation.components

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable


@Composable
fun CustomisedTextField(
    inputText : String,
    onValueChange : (String) -> Unit,
    placeHolder : @Composable (() -> Unit)? = null,

){
    TextField(
        value = inputText,
        onValueChange = onValueChange,
        placeholder = placeHolder
    )
}






