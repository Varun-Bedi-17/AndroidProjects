package com.example.goodspacesample.domain.repositories

import com.example.goodspacesample.data.models.LoginRequest
import com.example.goodspacesample.data.models.LoginResponse
import retrofit2.Response

interface GetOtpForLoginRepo {

    suspend fun getOtpForLoginFromRepo(authorization: String, deviceId: String, request: LoginRequest): Response<LoginResponse>
}