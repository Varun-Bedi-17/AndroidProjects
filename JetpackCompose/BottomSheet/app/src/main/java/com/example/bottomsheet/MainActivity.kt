package com.example.bottomsheet


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.bottomsheet.ui.theme.BottomSheetTheme
import kotlinx.coroutines.launch
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.*


@OptIn(ExperimentalMaterialApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val bottomSheetState =
                rememberBottomSheetState(
                    initialValue = BottomSheetValue.Collapsed,
//                    animationSpec = tween(durationMillis = 5000, easing = FastOutSlowInEasing)
                )
            val scaffoldState =
                rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
            val scope = rememberCoroutineScope()
            var previousProgress by remember { mutableStateOf(0f) }
            var arrow by remember {
                mutableStateOf(R.drawable.up_arrow)
            }
            var progressForArrow by remember{ mutableStateOf(0f) }

            BottomSheetTheme {
                BottomSheetScaffold(sheetContent = {
                    Box(
                        modifier = Modifier
                            .height(350.dp)
                            .fillMaxWidth()
                            .background(color = Color.Green,
                                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                            .pointerInput(Unit) {
                                detectDragGestures { change, dragAmount ->

                                    // For image
                                    val progress = bottomSheetState.progress.fraction
                                    val direction = progress - previousProgress
                                    bottomSheetState.performDrag(dragAmount.y)
                                    previousProgress = progress
                                    if (direction > 0) {
                                        arrow = R.drawable.up_arrow
                                    } else {
                                        arrow = R.drawable.down_arrow
                                    }

                                    // For arrow
                                    progressForArrow = bottomSheetState.progress.fraction
                                }
                            },
                        contentAlignment = Alignment.TopCenter
                    ) {


                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            DrawLine(offSetY = progressForArrow)
                            Image(
                                painter = painterResource(id = arrow),
                                contentDescription = "arrow",
                                modifier = Modifier.size(24.dp)
                            )
                            Text(text = stringResource(id = R.string.bottom_sheet),
                                fontSize = 30.sp)
                        }
                    }
                },
                    scaffoldState = scaffoldState,
//                    sheetPeekHeight = 0.dp,
//                    sheetContentColor = Color.Yellow
                    sheetGesturesEnabled = true

                ) {
                    Button(onClick = {
                        scope.launch {
                            bottomSheetState.expand()
                        }
                    }) {
                        Text(text = "Show Sheet ${bottomSheetState.progress.fraction}")
                    }
                }
            }
        }
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
                end = Offset(this.size.width * 0.5f, this.size.height * offSetY),
                strokeWidth = 5f
            )
            drawLine(
                color = Color.Black,
                start = Offset(this.size.width * 0.5f, this.size.height * offSetY),
                end = Offset(this.size.width, this.size.height),
                strokeWidth = 5f
            )
        }
        
    }
}







