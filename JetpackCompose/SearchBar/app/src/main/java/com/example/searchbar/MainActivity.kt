package com.example.searchbar

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.searchbar.ui.theme.SearchBarTheme
import org.w3c.dom.Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchBarTheme {
                Scaffold(
                    bottomBar = {BottomNavigationHome()}
                ) {
                    HomeScreen()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ShowPreview() {
    AlignYourBodyRow(modifier = Modifier)
}

@Composable
fun AlignYourBodyTab(
    @DrawableRes image: Int,
    @StringRes text: Int,
    modifier: Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(10.dp)
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
        )
        Text(text = stringResource(id = text))
    }
}


@Composable
fun SearchBar(
    modifier: Modifier,
) {
    TextField(value = "",
        onValueChange = {},
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        placeholder = { Text(stringResource(R.string.search)) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.LightGray
        ),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 60.dp)
            .padding(10.dp)
    )
}

@Composable
fun FavouriteCollectionCard(
    @DrawableRes image: Int,
    @StringRes text: Int,
    modifier: Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .width(200.dp)
            .padding(10.dp)
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(58.dp)
        )
        Text(
            text = stringResource(id = text),
            modifier = Modifier.padding(horizontal = 10.dp)
        )
    }
}


@Composable
fun AlignYourBodyRow(
    modifier: Modifier,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 10.dp),
        modifier = modifier
    ) {
        val alignBodyItems = listOf(
            AlignBodyItems(R.drawable.ab1_inversions, R.string.inversions),
            AlignBodyItems(R.drawable.ab2_quick_yoga, R.string.quick_yoga),
            AlignBodyItems(R.drawable.ab3_stretching, R.string.stretching),
            AlignBodyItems(R.drawable.ab4_tabata, R.string.tabata),
            AlignBodyItems(R.drawable.ab5_hiit, R.string.hiit),
            AlignBodyItems(R.drawable.ab6_pre_natal_yoga, R.string.pre_natal_yoga)
        )

        items(alignBodyItems) { item ->
            AlignYourBodyTab(image = item.image, text = item.text, modifier = Modifier)
        }
    }
}


@Composable
fun FavouriteCollectionGrid(
    modifier: Modifier,
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 10.dp),
        modifier = modifier.height(160.dp)
    ) {
        val favouriteCollectionItem = listOf(
            AlignBodyItems(R.drawable.fc1_short_mantras, R.string.short_mantras),
            AlignBodyItems(R.drawable.fc2_nature_meditations, R.string.nature_meditations),
            AlignBodyItems(R.drawable.fc3_stress_and_anxiety, R.string.stress_and_anxiety),
            AlignBodyItems(R.drawable.fc4_self_massage, R.string.self_massage),
            AlignBodyItems(R.drawable.fc5_overwhelmed, R.string.overwhelmed),
            AlignBodyItems(R.drawable.fc6_nightly_wind_down, R.string.nightly_wind_down)
        )

        items(favouriteCollectionItem) { item ->
            FavouriteCollectionCard(image = item.image, text = item.text, modifier = modifier)
        }

    }
}

@Composable
fun TitleWithGrid(
    @StringRes text: Int,
    modifier: Modifier,
    content: @Composable () -> Unit,
) {
    Column(modifier) {
        Text(
            text = stringResource(id = text),
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(start = 18.dp, top = 20.dp)
        )
        content()
    }
}


@Composable
fun HomeScreen() {
    Column(Modifier.verticalScroll(rememberScrollState())) {
        SearchBar(modifier = Modifier)
        TitleWithGrid(
            text = R.string.align_your_body,
            modifier = Modifier
        ) {
            AlignYourBodyRow(modifier = Modifier)
        }
        TitleWithGrid(
            text = R.string.favourites,
            modifier = Modifier,
            content = {
                FavouriteCollectionGrid(
                    modifier = Modifier)
            }
        )
    }
}


@Composable
fun BottomNavigationHome() {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background
    ) {
        BottomNavigationItem(
            selected = true,
            onClick = {},
            icon = {Icon(imageVector = Icons.Default.Home, null)},
            label = {Text(text = stringResource(R.string.home))}
        )
        BottomNavigationItem(
            selected = false,
            onClick = {},
            icon = {Icon(imageVector = Icons.Default.Person, null)},
            label = {Text(text = stringResource(R.string.profile))}
        )

    }
}