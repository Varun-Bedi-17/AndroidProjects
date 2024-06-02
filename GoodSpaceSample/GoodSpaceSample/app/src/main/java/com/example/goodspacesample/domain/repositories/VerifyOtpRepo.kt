package com.example.goodspacesample.domain.repositories

import com.example.goodspacesample.data.models.LoginResponse
import com.example.goodspacesample.data.models.VerifyRequest
import retrofit2.Response

interface VerifyOtpRepo {
    suspend fun verifyOtpForLoginFromRepo(authorization: String, deviceId: String, request: VerifyRequest): Response<LoginResponse>
}