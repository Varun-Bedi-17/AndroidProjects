package com.example.instagramclone.utils

sealed class Screens(val route : String){
    object SplashScreen : Screens("splash_screen")
    object LoginScreen : Screens("login_screen")
    object SignupScreen : Screens("signup_screen")
    object HomeScreen : Screens("home_screen")
    object SearchScreen : Screens("search_screen")
    object ProfileScreen : Screens("profile_screen")
}
