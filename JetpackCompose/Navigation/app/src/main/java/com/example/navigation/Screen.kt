package com.example.navigation

sealed class Screen(val route : String){
    object Home : Screen("home_screen")
    object Detail : Screen("detail_screen")

    fun withArgs(vararg args : String?) : String{
        return buildString {
            append(route)
            args.forEach { arg ->
                if(arg != ""){
                    append("/$arg")
                }
            }
        }
    }
}
