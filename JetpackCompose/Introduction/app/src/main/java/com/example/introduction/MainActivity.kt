package com.example.introduction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.introduction.ui.theme.IntroductionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            /** They will be overlap with each other */
//            Text("Welcome to the programming world")
//            Greeting(name = "World")

            /** We have rows and columns in compose */
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Blue),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text("jknuhuihi")
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Cyan, shape = CircleShape),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Row 1")
                Greeting(name = "World")
            }

            Row(
                modifier =
                Modifier
                    .width(200.dp)
                    .background(Color.Green),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "hvjhbjmbmj")
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
//    Greeting("Android")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text("jknuhuihi")
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Cyan, shape = CircleShape),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Row 1")
        Greeting(name = "World")
    }

    Row(
        modifier =
        Modifier
            .fillMaxSize(0.3f)
            .background(Color.Green),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "hvjhbjmbmj")
    }

}