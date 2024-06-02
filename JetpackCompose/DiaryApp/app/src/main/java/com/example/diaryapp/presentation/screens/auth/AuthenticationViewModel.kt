package com.example.diaryapp.presentation.screens.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AuthenticationViewModel : ViewModel(){
    var loadingState = mutableStateOf(false)
        private set


    fun setLoading(loading : Boolean){
        loadingState.value = loading
    }



}