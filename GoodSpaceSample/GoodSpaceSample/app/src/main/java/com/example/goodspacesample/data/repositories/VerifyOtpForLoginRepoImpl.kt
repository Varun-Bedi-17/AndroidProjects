package com.example.goodspacesample.data.repositories

import com.example.goodspacesample.data.models.LoginResponse
import com.example.goodspacesample.data.models.VerifyRequest
import com.example.goodspacesample.data.remote.api.ApiService
import com.example.goodspacesample.domain.repositories.VerifyOtpRepo
import retrofit2.Response
import javax.inject.Inject

class VerifyOtpForLoginRepoImpl @Inject constructor(private val apiService: ApiService) : VerifyOtpRepo{

    override suspend fun verifyOtpForLoginFromRepo(
        authorization: String,
        deviceId: String,
        request: VerifyRequest,
    ): Response<LoginResponse> {
        return apiService.verifyOtpForLogin(authorization, deviceId, request)
    }


}