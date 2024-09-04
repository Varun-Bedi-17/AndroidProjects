package com.example.loanapplication.data.repositories

import com.example.loanapplication.data.model.UserAuthResponse
import com.example.loanapplication.data.source.remote.ApiService
import com.example.loanapplication.domain.models.LoginRequest
import com.example.loanapplication.domain.repositories.LoginRepo
import retrofit2.Response
import javax.inject.Inject

class LoginRepoImpl @Inject constructor(
    private val apiService: ApiService,
) : LoginRepo {

    override suspend fun loginFromRepo(
        username: String,
        password: String
    ): UserAuthResponse = apiService.login(LoginRequest(username, password))
}