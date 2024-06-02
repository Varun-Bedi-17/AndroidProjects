package com.example.instagramclone.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagramclone.data.UserRepoImpl
import com.example.instagramclone.domain.model.Response
import com.example.instagramclone.domain.model.User
import com.example.instagramclone.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserViewModel(private val firebaseAuth: FirebaseAuth, private val repository: UserRepoImpl) : ViewModel() {

    private val userId = firebaseAuth.currentUser?.uid

    private val _getUserData = mutableStateOf<Response<User?>>(Response.Success(null))
    val getUserData : State<Response<User?>> = _getUserData

    private val _setUserData = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val setUserData : State<Response<Boolean>> = _setUserData

    fun getUserInfo(){
        if(userId != null){
            viewModelScope.launch {
                repository.getUserDataFromFirebase(userId).collect{
                    _getUserData.value = it
                }
            }
        }
    }

    fun setUserInfo(user : User){
        if (userId != null){
            viewModelScope.launch {
                repository.setUserDetails(user).collect{
                    _setUserData.value = it
                }
            }
        }
    }
}