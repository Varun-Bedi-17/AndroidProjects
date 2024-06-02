package com.example.selectedelements

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.selectedelements.ui.theme.SelectedElementsTheme
import com.example.selectedelements.ui.theme.Teal200

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val topicsList = listOf<Topics>(
                Topics(),
                Topics(),
                Topics(),
                Topics(),
                Topics(),
                Topics(),
                Topics(),
                Topics(),
                Topics(),
                Topics(),
                Topics(),
                Topics(),
                Topics(),
                Topics(),
                Topics(),
                Topics(),
                Topics(),
                Topics(),
                Topics(),
                Topics(),
                Topics(),
                Topics(),
                Topics(),
                Topics()
            )

            SelectedElementsTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Yellow),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    TopBar()
                    Text(
                        text = "Choose Topics that interest you",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    TopicGrid(topicsList)
                }
//                Screen()
            }
        }
    }
}


@Composable
fun TopBar() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        backgroundColor = Color.Yellow,
        elevation = 0.dp,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = stringResource(R.string.logo),
                modifier = Modifier.clip(CircleShape)
            )
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}


@Composable
fun TopicCard(topics: Topics) {
    var isSelected by remember {
        mutableStateOf(false)
    }
    val radius = if (isSelected) 60.dp else 0.dp

    Card(shape = RoundedCornerShape(
        topStart = radius
    ),
        modifier = Modifier
            .padding(5.dp)
            .clickable {
                isSelected = !isSelected
            }
    ) {
        Row(Modifier.wrapContentHeight()) {
            Image(
                painter = painterResource(id = topics.image),
                contentDescription = "Building Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = topics.message,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        if (isSelected) {
            Icon(
                Icons.Default.Check,
                null,
                modifier = Modifier.padding(top = 50.dp),
                tint = Teal200
            )

        }
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopicGrid(itemList: List<Topics>) {
    val lazyListState = rememberLazyListState()
    val isScrollingUp = lazyListState.isScrollingUp()

    Box {
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            content = {
                items(items = itemList) {
                    TopicCard(topics = Topics())
                }
            },
            state = lazyListState
        )

        FloatingActionButton(
            onClick = {},
            backgroundColor = Color.Magenta,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 20.dp, end = 20.dp)
        ) {
            Log.d("isScrollingUp", "isScrollingUpState.value = ${lazyListState.isScrollingUp()}")

            Box {
                Row {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    AnimatedVisibility(visible = isScrollingUp, modifier = Modifier.padding(horizontal = 5.dp)) {
                        Text("Add")
                    }
                }

            }
        }
    }
}




@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun Screen() {

    val listState = rememberLazyListState()
    val fabVisibility = listState.isScrollingUp()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            Modifier.fillMaxSize(),
            state = listState,
        ) {
            items(count = 100, key = { it.toString() }) {
                Text(modifier = Modifier.fillMaxWidth(),
                    text = "Hello $it!")
            }
        }

        AddPaymentFab(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            isVisibleBecauseOfScrolling = fabVisibility
        )
    }
}

@Composable
private fun AddPaymentFab(
    modifier: Modifier,
    isVisibleBecauseOfScrolling: Boolean,
) {
    val density = LocalDensity.current
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisibleBecauseOfScrolling,
        enter = slideInVertically {
            with(density) { 40.dp.roundToPx() }
        } + fadeIn(),
        exit = fadeOut(
            animationSpec = keyframes {
                this.durationMillis = 120
            }
        )
    ) {
        ExtendedFloatingActionButton(
            text = { Text(text = "Add Payment") },
            onClick = { },
            icon = { Icon(Icons.Filled.Add, "Add Payment") }
        )
    }
}