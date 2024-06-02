package com.example.instagramclone.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.instagramclone.R
import com.example.instagramclone.domain.model.Response
import com.example.instagramclone.presentation.authentication.AuthenticationViewModel
import com.example.instagramclone.utils.Screens

@Composable
fun SignUpScreen(navHostController: NavHostController, authenticationViewModel: AuthenticationViewModel) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()) {
        var userNameState by remember {
            mutableStateOf("")
        }
        var emailState by remember {
            mutableStateOf("")
        }
        var passwordState by remember {
            mutableStateOf("")
        }
        var isShowProgressIndicator by remember{
            mutableStateOf(false)
        }
        val text = stringResource(R.string.already_user)
        val startIndex = text.indexOf(stringResource(R.string.sign))
        val endIndex = startIndex + stringResource(R.string.sign_up).length

        InstagramIcon(size = 180.dp)
        Text(text = stringResource(id = R.string.sign_up), fontSize = 30.sp)
        OutlinedTextField(value = userNameState,
            onValueChange = {
                userNameState = it
            },
            modifier = Modifier.padding(10.dp),
            label = { Text(text = stringResource(id = R.string.enter_username)) })
        OutlinedTextField(value = emailState,
            onValueChange = {
                emailState = it
            },
            modifier = Modifier.padding(10.dp),
            label = { Text(text = stringResource(id = R.string.enter_mail)) })
        OutlinedTextField(value = passwordState,
            onValueChange = {
                passwordState = it
            },
            modifier = Modifier.padding(10.dp),
            label = { Text(text = stringResource(id = R.string.enter_pass)) })

        Button(onClick = {
            authenticationViewModel.signUp(email = emailState, password = passwordState, username = userNameState)
        }
        ) {when(val response = authenticationViewModel.signUpState.value){
            is Response.Loading -> {
                isShowProgressIndicator = true
            }
            is Response.Success ->{
                if(response.data){
                    LaunchedEffect(key1 = true) {
                        navHostController.navigate(Screens.HomeScreen.route) {
                            popUpTo(Screens.LoginScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                }
                else{
                    Toast(message = stringResource(R.string.sign_up_failed))
                }
            }
            is Response.Error -> {
                Toast(message = response.message)
            }
        }

            if(isShowProgressIndicator){
                CircularProgressIndicator(color = Color.White)
            }
            else {
                Text(text = stringResource(id = R.string.sign_in))
            }
        }

        val annotatedString = buildAnnotatedString {
            append(text)
            addStyle(
                style = SpanStyle(color = MaterialTheme.colors.primary),
                start = startIndex,
                end = endIndex
            )
        }
        ClickableText(
            text = annotatedString,
            onClick = { offset ->
                if (offset in startIndex..endIndex) {
                    navHostController.navigate(Screens.LoginScreen.route) {
                        launchSingleTop = true
                        popUpTo(Screens.SignupScreen.route){
                            inclusive = true
                        }
                    }
                }
            },
            style = TextStyle(fontSize = 15.sp),
            modifier = Modifier.padding(top = 25.dp)
        )
    }
}