package com.example.loanapplication.domain.repositories

import com.example.loanapplication.data.model.UserAuthResponse

interface SignupRepo {
    suspend fun signUpFromRepo(phone: String, username: String, password: String): UserAuthResponse
}