package com.example.loanapplication.domain.repositories

import com.example.loanapplication.data.model.UserAuthResponse

interface LoginRepo {
    suspend fun loginFromRepo(username: String, password: String): UserAuthResponse
}