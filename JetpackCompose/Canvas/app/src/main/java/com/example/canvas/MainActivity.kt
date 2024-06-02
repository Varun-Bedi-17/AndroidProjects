package com.example.canvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.canvas.ui.theme.CanvasTheme
import org.w3c.dom.Text


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var offSetY by  remember{mutableStateOf(30f)}
            CanvasTheme {
                Row(modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            offSetY = dragAmount.y
                        }
                    }) {
                    InstagramIcon()
                    FacebookIcon()
                    MessengerIcon()
                    DrawLine(offSetY)
                }
            }
        }
    }
}


@Composable
fun InstagramIcon() {
    val instaColors = listOf(Color.Yellow, Color.Red, Color.Magenta)
    Canvas(
        modifier = Modifier
            .size(100.dp)
            .padding(16.dp)
    ) {
        drawRoundRect(
            brush = Brush.linearGradient(colors = instaColors),
            cornerRadius = CornerRadius(60f, 60f),
            style = Stroke(width = 15f, cap = StrokeCap.Round)
        )
        drawCircle(
            brush = Brush.linearGradient(colors = instaColors),
            radius = 45f,
            style = Stroke(width = 15f, cap = StrokeCap.Round)
        )
        drawCircle(
            brush = Brush.linearGradient(colors = instaColors),
            radius = 13f,
            center = Offset(this.size.width * .80f, this.size.height * 0.20f),
        )
    }
}


@Composable
fun FacebookIcon() {

    val paint = android.graphics.Paint().apply {
       textAlign = android.graphics.Paint.Align.CENTER
       textSize = 200f
       color = Color.White.toArgb()
   }
    Canvas(modifier = Modifier
        .size(100.dp)
        .padding(10.dp)){
        drawRoundRect(
            cornerRadius = CornerRadius(20f, 20f),
            color = Color.Blue
        )

        drawContext.canvas.nativeCanvas.drawText("f", center.x+20 , center.y+80, paint)
    }
}


@Composable
fun MessengerIcon() {

    val colors = listOf(Color(0xFF02b8f9), Color(0xFF0277fe))

    Canvas(modifier = Modifier
        .size(100.dp)
        .padding(10.dp)
        ){

        val triangularPath = Path().let {
            it.moveTo(this.size.width * .20f, this.size.height * .77f)
            it.lineTo(this.size.width * .20f, this.size.height * 0.95f)
            it.lineTo(this.size.width * 0.37f, this.size.height * 0.86f)
            it.close()
            it
        }
        val electricPath = Path().let {
            it.moveTo(this.size.width * .20f, this.size.height * 0.60f)
            it.lineTo(this.size.width * .45f, this.size.height * 0.35f)
            it.lineTo(this.size.width * 0.56f, this.size.height * 0.46f)
            it.lineTo(this.size.width * 0.78f, this.size.height * 0.35f)
            it.lineTo(this.size.width * 0.54f, this.size.height * 0.60f)
            it.lineTo(this.size.width * 0.43f, this.size.height * 0.45f)
            it.close()
            it
        }
        drawOval(
            brush = Brush.verticalGradient(colors),
            size = Size(this.size.width, this.size.height * 0.95f)
        )

        drawPath(
            path = triangularPath,
            brush = Brush.verticalGradient(colors),
            style = Stroke(width = 15f, cap = StrokeCap.Round)
        )
        drawPath(
            path = electricPath,
            color = Color.White
        )

    }
}


@Composable
fun DrawLine(offSetY: Float) {
    Column {
        Canvas(
            modifier = Modifier
                .width(50.dp)
                .height(30.dp)
                .padding(10.dp)
        ) {
            drawLine(
                color = Color.Black,
                start = Offset(0f, this.size.height),
                end = Offset(this.size.width * 0.5f, offSetY),
                strokeWidth = 2f
            )
            drawLine(
                color = Color.Black,
                start = Offset(this.size.width * 0.5f, offSetY),
                end = Offset(this.size.width, this.size.height),
                strokeWidth = 2f
            )
        }
        
        Text(text = "$offSetY", fontSize = 20.sp)
    }
}
