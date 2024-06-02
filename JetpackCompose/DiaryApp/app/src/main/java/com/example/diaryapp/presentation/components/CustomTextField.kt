package com.example.diaryapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    text: String,
    onTextChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    leadingIcon : @Composable (() -> Unit)? = null,
    trailingIcon : @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
){
    Column(
        modifier = modifier
    ){
        Row(
            modifier = Modifier.height(35.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            leadingIcon?.apply {
                this.invoke()
                Spacer(modifier = Modifier.width(10.dp))
            }
            PlaceholderTextField(
                text = text,
                onTextChange = onTextChange,
                placeholder = placeholder
            )
            trailingIcon?.apply {
                Spacer(modifier = Modifier.width(10.dp))
                this.invoke()
            }
        }
        Spacer(modifier = Modifier.background(color = Color.White).fillMaxWidth().height(2.dp))
    }
}

@Preview
@Composable
fun ShowPreview(){
    CustomTextField(
        text = "",
        onTextChange = {  },
        placeholder = "Email ID",
        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = Color.White) }
    )
}