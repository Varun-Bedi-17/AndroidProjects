package com.example.loanapplication.data.repositories

import com.example.loanapplication.data.model.UserAuthResponse
import com.example.loanapplication.data.source.remote.ApiService
import com.example.loanapplication.domain.models.SignupRequest
import com.example.loanapplication.domain.repositories.SignupRepo
import retrofit2.Response
import javax.inject.Inject

class SignupRepoImpl @Inject constructor(
    private val apiService: ApiService
) : SignupRepo {

    override suspend fun signUpFromRepo(
        phone: String,
        username: String,
        password: String
    ): UserAuthResponse = apiService.signup(SignupRequest(phone, username, password))
}