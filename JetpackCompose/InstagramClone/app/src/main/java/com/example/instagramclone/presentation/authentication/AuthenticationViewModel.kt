package com.example.instagramclone.presentation.authentication

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagramclone.data.AuthenticationRepoImpl
import com.example.instagramclone.domain.model.Response
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val repositoryImpl: AuthenticationRepoImpl) : ViewModel() {

    val isUserAuthenticated = repositoryImpl.isUserAuthenticated()

    private val _signInState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signInState: State<Response<Boolean>> = _signInState

    private val _signOutState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signOutState: State<Response<Boolean>> = _signOutState

    private val _signUpState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signUpState: State<Response<Boolean>> = _signUpState

    private val _firebaseAuthState  = mutableStateOf(false)
    val firebaseAuthState : State<Boolean> = _firebaseAuthState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            repositoryImpl.firebaseSignIn(email, password).collect {
                _signInState.value = it
            }
        }
    }

    fun signUp(email: String, password: String, username : String) {
        viewModelScope.launch {
            repositoryImpl.firebaseSignUp(email, password, username).collect{
                _signUpState.value = it
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            repositoryImpl.firebaseSignOut().collect{
                _signOutState.value = it
                if(it == Response.Success(false)){
                    _signInState.value = Response.Success(false)
                }
            }
        }
    }

    fun getFirebaseAuthState(){
        viewModelScope.launch {
            repositoryImpl.getFirebaseAuthState().collect{
                _firebaseAuthState.value = it
            }
        }
    }
}