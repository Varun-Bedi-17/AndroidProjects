package com.example.splashscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.splashscreen.ui.theme.SplashScreenTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isShowSplashScreen by remember{ mutableStateOf(true) }
            if(isShowSplashScreen){
                SplashScreen(onTimeout = {isShowSplashScreen = false})
            }
            else{
                HomeScreen()
            }
        }
    }
}


@Composable
fun SplashScreen(onTimeout : () -> Unit) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Magenta), contentAlignment = Alignment.Center){

        val currentOnTimeout by rememberUpdatedState(onTimeout)

        LaunchedEffect(key1 = Unit){
            delay(2000)
            currentOnTimeout()
        }
        Text(text = "Welcome to the Splash Screen", fontSize = 30.sp, textAlign = TextAlign.Center)
    }
}


@Composable
fun HomeScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Red), contentAlignment = Alignment.Center){
        Text(text = "Home Screen", fontSize = 30.sp)
    }
}