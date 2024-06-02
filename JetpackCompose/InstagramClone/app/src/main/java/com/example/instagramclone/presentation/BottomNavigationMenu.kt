package com.example.instagramclone.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.instagramclone.utils.Screens


@Composable
fun BottomNavigationMenu(selectedItem: BottomNavigationItem, navController: NavHostController) {

    Card(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(Color.White),
        elevation = 5.dp
    )
    {
        Row {
            for (item in BottomNavigationItem.values()) {
                Image(imageVector = item.icon,
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .size(45.dp)
                        .padding(5.dp)
                        .clickable {
                            navController.navigate(item.route)
                        },
                    colorFilter = if (item == selectedItem) ColorFilter.tint(Color.Black) else ColorFilter.tint(
                        Color.LightGray)
                )
            }

        }
    }
}

enum class BottomNavigationItem(val icon: ImageVector, val route: String) {
    HOME(icon = Icons.Default.Home, route = Screens.HomeScreen.route),
    SEARCH(icon = Icons.Default.Search, route = Screens.SearchScreen.route),
    PROFILE(icon = Icons.Default.Person, route = Screens.ProfileScreen.route)
}