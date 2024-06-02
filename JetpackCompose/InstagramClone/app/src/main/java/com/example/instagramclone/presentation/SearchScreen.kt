package com.example.instagramclone.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.instagramclone.R

@Composable
fun SearchScreen(navController :NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = stringResource(R.string.search),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        BottomNavigationMenu(selectedItem = BottomNavigationItem.SEARCH, navController = navController)
    }
}