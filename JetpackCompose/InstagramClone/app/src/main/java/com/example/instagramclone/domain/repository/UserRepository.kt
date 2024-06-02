package com.example.instagramclone.domain.repository

import com.example.instagramclone.domain.model.Response
import com.example.instagramclone.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserDataFromFirebase(userId : String) : Flow<Response<User>>
    fun setUserDetails(user : User) : Flow<Response<Boolean>>
}