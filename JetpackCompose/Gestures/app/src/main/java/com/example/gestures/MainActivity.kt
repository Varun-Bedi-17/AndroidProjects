package com.example.gestures

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestures.ui.theme.GesturesTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GesturesTheme {

                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {

                    /** Tap Gestures */
                    // TapGestures()

                    /** Vertical Slide */
                    // VerticalSlide()

                    /** Dragging */
                    Dragging()
                }
            }
        }
    }
}

@Composable
fun TapGestures() {
    var count by remember {
        mutableStateOf(0)
    }
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Hello $count", fontSize = 30.sp, modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(
                onPress = {
                    Log.d("Gestures", "onPress")
                    count += 1
                },
                onTap = {
                    Log.d("Gestures", "onTap")
                    count += 2
                },
                onDoubleTap = {
                    Log.d("Gestures", "onDoubleTap")
                    count += 5
                },
                onLongPress = {
                    Log.d("Gestures", "onLongPress")
                    count += 10
                }
            )
        })
    }

}

@Composable
fun VerticalSlide() {
    var count by remember {
        mutableStateOf(0)
    }
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier =  Modifier
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollableState { delta ->
                    count -= delta.toInt()
                    delta
                }
            )) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null)
        Text(
            text = if (count < 0) "0" else if (count in 0..99) "$count" else "100",
            fontSize = 30.sp
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Dragging() {

    /*Box(modifier = Modifier.fillMaxSize()) {
        var offsetX by remember{ mutableStateOf(0f) }
        var offsetY by remember{ mutableStateOf(0f) }
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .background(Color.Blue)
                .width(50.dp)
                .height(50.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
        )
    }*/

    var offsetX by remember { mutableStateOf(0f) }
    Box {
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .background(Color.Blue)
                .width(50.dp)
                .height(50.dp)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState {delta ->
                        offsetX += delta
                    }
                )
        )
    }


    /*Text(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), 0) }
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    offsetX += delta
                }
            ),
        text = "Drag me!"
    )*/
}


