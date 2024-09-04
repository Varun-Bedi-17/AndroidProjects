package com.example.loanapplication.data.source.remote

import com.example.loanapplication.data.model.UserAuthResponse
import com.example.loanapplication.domain.models.LoginRequest
import com.example.loanapplication.domain.models.SignupRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/sign_in")
    suspend fun login(@Body request: LoginRequest): UserAuthResponse

    @POST("/sign_up")
    suspend fun signup(@Body request: SignupRequest): UserAuthResponse
}