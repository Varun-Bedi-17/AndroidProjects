package com.example.cleanappui

import android.bluetooth.BluetoothAdapter.LeScanCallback
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cleanappui.ui.theme.BG_COLOR
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState


class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val images = listOf(painterResource(id = R.drawable.ic_cleaning),
                painterResource(id = R.drawable.img),
                painterResource(id = R.drawable.img_1)
            )
            val pagerState = rememberPagerState()
            HorizontalScrollRow(images = images, pagerState = pagerState)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalScrollRow(images : List<Painter>, pagerState : PagerState) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = BG_COLOR)
        .padding(10.dp)) {
        Box(modifier = Modifier
            .fillMaxHeight(0.8f)
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(40.dp)),
            contentAlignment = Alignment.Center) {
            HorizontalPager(count = images.size, state = pagerState) { page ->
                Image(painter = images[page], contentDescription = null, contentScale = ContentScale.Fit, modifier = Modifier.fillMaxSize())
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        RowIndicators(pageCount = images.size, currentPage = pagerState.currentPage)
    }
}


@Composable
fun RowIndicators(pageCount :Int, currentPage :Int){
    Row(horizontalArrangement = Arrangement.spacedBy(space = 3.dp,
        alignment = Alignment.CenterHorizontally),
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        repeat(pageCount){
            Box(modifier = Modifier
                .size(if (currentPage == it) 15.dp else 10.dp)
                .clip(shape = CircleShape)
                .background(color = if (currentPage == it) Color.White else Color.LightGray)
            )
        }

    }
}