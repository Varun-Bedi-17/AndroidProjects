package com.example.textfieldsandbuttons

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Greeting()
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun Greeting() {
    val viewModel: SubmitDiagnosticReportViewModel = viewModel()

    Column {
        InputFields(
            text = "Customer Name",
            placeholderText = "Enter your name",
            inputText = viewModel.inputName,
            { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
            Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        InputFields(
            text = "Customer Email",
            placeholderText = "Enter your email",
            inputText = viewModel.inputEmail,
            { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
            Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        InputFields(
            text = "Issue Description",
            placeholderText = "Enter your issue",
            inputText = viewModel.inputIssueDescription,
            null,
            Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        Button(
            onClick = { viewModel.isValidateLayout() },
            modifier = Modifier.fillMaxWidth(),
        ) {

        }
        Row{
            BasicTextField(value = "jkscakjnakjc", onValueChange = {})
            Icon(imageVector = Icons.Default.Email, contentDescription = null)     }
    }
}


@Composable
fun InputFields(
    text: String,
    placeholderText: String,
    inputText: String,
    iconImage: @Composable() (() -> Unit)?,
    modifier: Modifier
) {
    val viewModel: SubmitDiagnosticReportViewModel = viewModel()

    Column(
        modifier = Modifier.padding(horizontal = 30.dp)
    ) {
        Text(
            text = text,
            fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
        )


        val maxLength = 200
        OutlinedTextField(
            value = inputText,
            onValueChange = { newText ->
                if (text == "Customer Name") {
                    viewModel.inputName = newText
                } else if (text == "Customer Email") {
                    viewModel.inputEmail = newText
                } else {
                    if (newText.length <= maxLength) {
                        viewModel.inputIssueDescription = newText
                    }
                }
            },
            modifier = modifier,
            placeholder = { Text(text = placeholderText) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                focusedBorderColor = colorResource(id = R.color.focussed_color),
                unfocusedBorderColor = colorResource(id = R.color.border_color)
            ),
            shape = RoundedCornerShape(10.dp),
            leadingIcon = iconImage
        )
    }
}


