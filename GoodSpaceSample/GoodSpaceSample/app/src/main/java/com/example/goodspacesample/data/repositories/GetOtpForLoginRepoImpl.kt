package com.example.goodspacesample.data.repositories

import com.example.goodspacesample.data.models.LoginRequest
import com.example.goodspacesample.data.models.LoginResponse
import com.example.goodspacesample.data.remote.api.ApiService
import com.example.goodspacesample.domain.repositories.GetOtpForLoginRepo
import retrofit2.Response
import javax.inject.Inject

class GetOtpForLoginRepoImpl @Inject constructor(private val apiService: ApiService) : GetOtpForLoginRepo{

    override suspend fun getOtpForLoginFromRepo(
        authorization: String,
        deviceId: String,
        request: LoginRequest
    ): Response<LoginResponse> {
        return apiService.getOtpForLogin(authorization, deviceId, request)
    }


}