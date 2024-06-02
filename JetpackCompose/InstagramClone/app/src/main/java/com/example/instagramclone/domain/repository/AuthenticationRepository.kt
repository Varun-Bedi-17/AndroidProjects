package com.example.instagramclone.domain.repository

import com.example.instagramclone.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    fun isUserAuthenticated() : Boolean

    fun getFirebaseAuthState() : Flow<Boolean>

    fun firebaseSignIn(email : String, password : String) : Flow<Response<Boolean>>

    fun firebaseSignOut() : Flow<Response<Boolean>>

    fun firebaseSignUp(email : String, password : String, username : String) : Flow<Response<Boolean>>
}