package com.example.diaryapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diaryapp.R

@Composable
fun SignInButton(
    modifier: Modifier = Modifier,
    loadingState: Boolean = false,
    primaryText: String = stringResource(id = R.string.sign_in),
    secondaryText: String = stringResource(id = R.string.please_wait),
    buttonGradientColors: List<Color> = listOf(
        colorResource(id = R.color.teal_200),
        colorResource(id = R.color.splash_bg_color)
    ),
    progressIndicatorColor : Color = colorResource(id = R.color.white),
    onClick: () -> Unit,
) {
    Button(onClick = onClick,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 15.dp,
            disabledElevation = 0.dp,
            pressedElevation = 30.dp
        ),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(),
        enabled = !loadingState,
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        var buttonText by remember{
            mutableStateOf(primaryText)
        }
        LaunchedEffect(key1 = loadingState){
            buttonText = if(loadingState) secondaryText else primaryText
        }
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(buttonGradientColors)
                )
                .fillMaxWidth(0.7f)
                .padding(vertical = 8.dp, horizontal = 30.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = buttonText, color = Color.White)
            if(loadingState){
                Spacer(modifier = Modifier.width(15.dp))
                CircularProgressIndicator(
                    strokeWidth = 2.dp,
                    color = progressIndicatorColor,
                    modifier = Modifier.size(10.dp)
                )
            }
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun SignInPreview(){
    Button(onClick = { /*TODO*/ },
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 15.dp,
            disabledElevation = 0.dp,
            pressedElevation = 30.dp
        ),
        shape = CutCornerShape(20),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(),
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            colorResource(id = R.color.teal_200),
                            colorResource(id = R.color.teal_500)
                        )
                    )
                )
                .padding(vertical = 8.dp, horizontal = 30.dp)
        ) {
            Text(text = stringResource(id = R.string.sign_in), color = Color.Black)
        }
    }
}