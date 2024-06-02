package com.example.diaryapp.presentation.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.diaryapp.MainActivity
import com.example.diaryapp.R
import com.example.diaryapp.presentation.components.CustomTextField
import com.example.diaryapp.presentation.components.SignInButton
import com.example.diaryapp.ui.theme.bg_end_gradient
import com.example.diaryapp.ui.theme.bg_start_gradient


@Composable
fun AuthenticationScreen(
    inputText : String,
    inputPassword : String,
    onInputTextChange : (String) -> Unit,
    onInputPasswordChange : (String) -> Unit,
    loadingState : Boolean,
    onSignInButtonClick : () -> Unit,
    onGoogleIconClick : () -> Unit,
    onFacebookIconClick : () -> Unit,
    onTwitterIconClick : () -> Unit,
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(
            Brush.linearGradient(
                colors = listOf(
                    bg_start_gradient,
                    bg_end_gradient,
                    bg_end_gradient,
                    bg_start_gradient
                )
            )
        ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(id = R.string.app_name),
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)

        )
        Spacer(modifier = Modifier.height(40.dp))
        CustomTextField(
            text = inputText,
            onTextChange = onInputTextChange,
            placeholder = stringResource(R.string.email_id),
            leadingIcon = { Icon(imageVector = Icons.Filled.Email, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp)) },
            modifier = Modifier.fillMaxWidth(0.7f)
        )
        Spacer(modifier = Modifier.height(25.dp))
        CustomTextField(
            text = inputPassword,
            onTextChange = onInputPasswordChange,
            placeholder = stringResource(R.string.password),
            leadingIcon = { Icon(imageVector = Icons.Filled.Lock, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp)) },
            modifier = Modifier.fillMaxWidth(0.7f)
        )
        Spacer(modifier = Modifier.height(30.dp))
        SignInButton(
            onClick = onSignInButtonClick,
            loadingState = loadingState,
            buttonGradientColors = listOf(
                colorResource(id = R.color.purple_800),
                colorResource(id = R.color.purple_500)
            )
        )
        Spacer(modifier = Modifier.height(70.dp))
        Text(text = stringResource(id = R.string.or_sign_in), color = Color.White)
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Image(painter = painterResource(id = R.drawable.google_logo),
                contentDescription = stringResource(id = R.string.google_logo),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape)
                    .clickable(
                        enabled = true,
                        onClick = onGoogleIconClick
                    )
                    .background(Color.Red)
            )
            Image(painter = painterResource(id = R.drawable.facebook_logo),
                contentDescription = stringResource(id = R.string.facebook_logo),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape)
                    .clickable(
                        enabled = true,
                        onClick = onFacebookIconClick
                    )
                    .background(Color.White)
            )
            Image(painter = painterResource(id = R.drawable.twitter_logo),
                contentDescription = stringResource(id = R.string.twitter_logo),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape)
                    .clickable(
                        enabled = true,
                        onClick = onTwitterIconClick
                    )
                    .background(Color.White)
            )
        }

    }
}



