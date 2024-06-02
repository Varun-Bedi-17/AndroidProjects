package com.example.diaryapp.navigation

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.diaryapp.R
import com.example.diaryapp.presentation.screens.auth.AuthenticationScreen
import com.example.diaryapp.utils.Constants
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException

@Composable
fun SetupNavGraph(startDestination: String, navController: NavHostController) {
    NavHost(startDestination = startDestination, navController = navController) {
        authenticationRoute()
        homeRoute()
        writeRoute()
    }
}

fun NavGraphBuilder.authenticationRoute() {
    composable(Screen.Authentication.route) {
        val context = LocalContext.current
        val oneTapClient = Identity.getSignInClient(context)
        val signUpRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(Constants.CLIENT_ID)
                    // Show all accounts on the device.
                    .setFilterByAuthorizedAccounts(false)
                    .build())
            .build()
        val resultLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if(result.resultCode == Activity.RESULT_OK){
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                    val idToken = credential.googleIdToken
                    when {
                        idToken != null -> {
                            // Got an ID token from Google. Use it to authenticate
                            // with your backend.
                            Log.d(Constants.TAG, "Got ID token.")
                            val email = credential.id
                            Toast.makeText(context,email, Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            // Shouldn't happen.
                            Log.d(Constants.TAG, "No ID token!")
                        }
                    }
                } catch (e: ApiException) {
                    e.printStackTrace()
                }
            }
        }

        var inputText by remember {
            mutableStateOf("")
        }
        var inputPassword by remember {
            mutableStateOf("")
        }



        AuthenticationScreen(
            inputText = inputText,
            inputPassword = inputPassword,
            onInputTextChange = { inputText =it },
            onInputPasswordChange = { inputPassword = it},
            loadingState = false,
            onSignInButtonClick = {},
            onGoogleIconClick = {},
            onFacebookIconClick = {},
            onTwitterIconClick = {},
        )
    }
}

fun NavGraphBuilder.homeRoute() {
    composable(Screen.Home.route) {

    }
}

fun NavGraphBuilder.writeRoute() {
    composable(route = Screen.Write.route,
        arguments = listOf(navArgument(
            name = Constants.WRITE_SCREEN_ARG
        ) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        }
        )
    ) {

    }
}


private fun signUpUsingGoogle(context: Context, signUpRequest: BeginSignInRequest, resultLauncher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>, oneTapClient: SignInClient) {


    oneTapClient.beginSignIn(signUpRequest)
        .addOnSuccessListener(context) { result ->
            try {
                val intentSenderRequest = IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                resultLauncher.launch(intentSenderRequest)
            } catch (e: IntentSender.SendIntentException) {
                Log.e(Constants.TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
            }
        }
        .addOnFailureListener(context) { e ->
            // No Google Accounts found. Just continue presenting the signed-out UI.
            Log.d(Constants.TAG, e.localizedMessage?.toString() ?: context.getString(R.string.something_wrong))
        }
}