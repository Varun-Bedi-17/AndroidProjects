package com.example.instagramclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.instagramclone.data.AuthenticationRepoImpl
import com.example.instagramclone.data.UserRepoImpl
import com.example.instagramclone.presentation.*
import com.example.instagramclone.presentation.authentication.AuthenticationViewModel
import com.example.instagramclone.presentation.profile.UserViewModel
import com.example.instagramclone.ui.theme.InstagramCloneTheme
import com.example.instagramclone.utils.Screens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            val authViewModel = AuthenticationViewModel(repositoryImpl = AuthenticationRepoImpl(
                fireStore = Firebase.firestore,
                auth = Firebase.auth)
            )
            val userViewModel : UserViewModel = UserViewModel(Firebase.auth, UserRepoImpl(Firebase.firestore))

            NavigationSetUp(navHostController = navHostController, authenticationViewModel = authViewModel, userViewModel = userViewModel)

        }
    }
}

@Composable
fun NavigationSetUp(navHostController: NavHostController, authenticationViewModel: AuthenticationViewModel, userViewModel: UserViewModel) {
    NavHost(navController = navHostController, startDestination = Screens.SplashScreen.route) {
        composable(route = Screens.LoginScreen.route) {
            LoginScreen(navHostController = navHostController, authenticationViewModel = authenticationViewModel)
        }
        composable(route = Screens.HomeScreen.route) {
            HomeScreen(navController = navHostController)
        }
        composable(route = Screens.SignupScreen.route) {
            SignUpScreen(navHostController = navHostController, authenticationViewModel = authenticationViewModel)
        }
        composable(route = Screens.SplashScreen.route) {
            SplashScreen(navController = navHostController, authViewModel = authenticationViewModel)
        }
        composable(route = Screens.SearchScreen.route) {
            SearchScreen(navController = navHostController)
        }
        composable(route = Screens.ProfileScreen.route) {
            ProfileScreen(navController = navHostController, userViewModel)
        }
    }

}
