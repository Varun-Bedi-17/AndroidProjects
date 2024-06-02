package com.example.permissions

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val permissionDeniedDialogsQueue = mutableStateListOf<String>()

    fun onPermissionResult(
        permission : String,
        isGranted : Boolean
    ){
        if(!isGranted){
            permissionDeniedDialogsQueue.add(permission)
        }
    }

    fun dismissDialog(){
        permissionDeniedDialogsQueue.removeFirst()
    }
}