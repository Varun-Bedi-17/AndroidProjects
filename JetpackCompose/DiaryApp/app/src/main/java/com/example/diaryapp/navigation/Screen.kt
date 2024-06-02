package com.example.diaryapp.navigation

import com.example.diaryapp.utils.Constants

sealed class Screen(val route : String){
    object Authentication : Screen("authentication_screen")
    object Home : Screen("home_screen")
    object Write : Screen("write_screen?${Constants.WRITE_SCREEN_ARG}={${Constants.WRITE_SCREEN_ARG}}"){
        fun passDiaryId(diaryId : String) =
            "write_screen?${Constants.WRITE_SCREEN_ARG}=$diaryId"
    }
}
