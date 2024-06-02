package com.example.instagramclone.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.instagramclone.R
import com.example.instagramclone.domain.model.Response
import com.example.instagramclone.domain.model.User
import com.example.instagramclone.presentation.profile.UserViewModel
import com.example.instagramclone.ui.theme.ButtonColor


@Composable
fun ProfileScreen(navController: NavHostController, userViewModel: UserViewModel) {
    LaunchedEffect(key1 = true) {
        userViewModel.getUserInfo()
    }
    var selectedTabIndex by remember{
        mutableStateOf(0)
    }
    val images = listOf(R.drawable.ic_grid, R.drawable.img_1)
    val imagesForSavedPosts = listOf(painterResource(id = R.drawable.img_2),
        painterResource(id = R.drawable.img_3),
        painterResource(id = R.drawable.img_4),
        painterResource(id = R.drawable.img_5),
        painterResource(id = R.drawable.img_6),
        painterResource(id = R.drawable.img_7),
        painterResource(id = R.drawable.img_8),
        painterResource(id = R.drawable.img_9),
        painterResource(id = R.drawable.img_10),
        painterResource(id = R.drawable.img_11),
        painterResource(id = R.drawable.img_3),
        painterResource(id = R.drawable.img_4),
        painterResource(id = R.drawable.img_5),
        painterResource(id = R.drawable.img_6),
        painterResource(id = R.drawable.img_7),
        painterResource(id = R.drawable.img_8),
        painterResource(id = R.drawable.img_9),
        painterResource(id = R.drawable.img_10)
    )
    val imagesForTaggedPosts = listOf(painterResource(id = R.drawable.img_2),
        painterResource(id = R.drawable.img_6),
        painterResource(id = R.drawable.img_7),
        painterResource(id = R.drawable.img_8),
        painterResource(id = R.drawable.img_9),
        painterResource(id = R.drawable.img_10),
        painterResource(id = R.drawable.img_11),
        painterResource(id = R.drawable.img_3),
        painterResource(id = R.drawable.img_4),
        painterResource(id = R.drawable.img_5),
        painterResource(id = R.drawable.img_7),
        painterResource(id = R.drawable.img_8),
        painterResource(id = R.drawable.img_9),
        painterResource(id = R.drawable.img_10),
        painterResource(id = R.drawable.img_11)

    )
    when (val response = userViewModel.getUserData.value) {
        is Response.Loading -> {
            CircularProgressIndicator()
        }
        is Response.Success -> {
            if (response.data != null) {
                val userDetails = response.data
                Column(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.White))
                    {
                        ProfileTopBar(userDetails)
                        ProfileSection(userDetails)
                        ProfileGridTabRow(selectedTabIndex, images) { index ->
                            selectedTabIndex = index
                        }
                        when(selectedTabIndex){
                            0 -> {PostsSection(imagesForSavedPosts)}
                            1 ->{TaggedSection(imagesForTaggedPosts)}
                        }
                    }
                    BottomNavigationMenu(selectedItem = BottomNavigationItem.PROFILE,
                        navController = navController)

                }
            }
        }
        is Response.Error -> {
        }

    }

}


@Composable
fun ProfileTopBar(userDetails: User) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)) {
        Text(text = userDetails.email, fontSize = 20.sp)
        Row {
            Image(imageVector = Icons.Outlined.Add,
                contentDescription = stringResource(R.string.add_icon), modifier = Modifier
                    .size(40.dp)
                    .padding(5.dp))
            Image(imageVector = Icons.Default.Menu, contentDescription = null, modifier = Modifier
                .size(40.dp)
                .padding(top = 5.dp, bottom = 5.dp))
        }
    }
}

@Composable
fun ProfileSection(userDetails : User){
    Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            if (userDetails.imageUrl != "") {
                AsyncImage(
                    model = userDetails.imageUrl,
                    contentDescription = stringResource(R.string.user_profile_image),
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            } else {
                Image(painter = painterResource(id = R.drawable.img),
                    contentDescription = stringResource(id = R.string.user_profile_image),
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(width = 2.dp, color = Color.LightGray, shape = CircleShape)
                        .padding(5.dp)
                )
            }

            PostsWithText(number= 200, text = stringResource(R.string.posts))
            PostsWithText(number= 150, text = stringResource(R.string.followers))
            PostsWithText(number= 187, text = stringResource(R.string.following))
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = userDetails.userName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        if(userDetails.bio != "") {
            Text(text = userDetails.bio, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }
        else{
            Text(text = stringResource(R.string.wite_something), color = Color.DarkGray, fontSize = 15.sp)
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor,
                    contentColor = Color.Black),
                modifier = Modifier.weight(3f)
            ) {
                Text(text = stringResource(R.string.edit_profile))
            }
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor,
                    contentColor = Color.Black),
                modifier = Modifier.weight(3f)
            ) {
                Text(text = stringResource(R.string.share_profile))
            }
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor,
                    contentColor = Color.Black),
                modifier = Modifier.weight(1f)
            ) {
                Image(imageVector = Icons.Default.Person, contentDescription = null, modifier = Modifier.size(20.dp))
            }
        }
    }
}

@Composable
fun PostsWithText(number: Int, text: String) {
    Column{
        Text(text = number.toString(), fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = text, fontSize = 15.sp)
    }
}

@Composable
fun ProfileGridTabRow(selectedTabIndex: Int, images: List<Int>, onTabSelected : (Int)->Unit) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = Color.White,
        modifier = Modifier.padding(top = 10.dp)
    ) {
        images.forEachIndexed {index, image->
            Tab(
                selected = selectedTabIndex == index,
                onClick = {
                    onTabSelected(index)
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.LightGray
            ){
                Icon(
                    painter = painterResource(id = image),
                    contentDescription = null,
                    tint = if(selectedTabIndex == index) Color.Black else Color.LightGray,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(bottom = 10.dp)
                )
            }
        }

    }
}

@Composable
fun PostsSection(images : List<Painter>) {
    LazyVerticalGrid(columns = GridCells.Fixed(3)){
        items(images.size){
            Image(painter = images[it], contentDescription = null, modifier = Modifier.aspectRatio(1f).border(1.dp, Color.White))
        }
    }
}

@Composable
fun TaggedSection(images : List<Painter>) {
    LazyVerticalGrid(columns = GridCells.Fixed(3)){
        items(images.size){
            Image(painter = images[it], contentDescription = null, modifier = Modifier.aspectRatio(1f).border(1.dp, Color.White))
        }
    }
}
