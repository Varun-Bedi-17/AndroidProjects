package com.example.goodspacesample.data.remote.api

import com.example.goodspacesample.data.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST


interface ApiService {

    @Headers(
        "Content-Type: application/json",
        "device-type: android"
    )
    @POST("api/d2/auth/v2/login")
    suspend fun getOtpForLogin(@Header("Authorization") authorization: String,@Header("device-id") deviceId: String,@Body request: LoginRequest): Response<LoginResponse>

    @Headers(
        "Content-Type: application/json",
        "device-type: android"
    )
    @POST("api/d2/auth/verifyOtp")
    suspend fun verifyOtpForLogin(@Header("Authorization") authorization: String,@Header("device-id") deviceId: String,@Body request: VerifyRequest): Response<LoginResponse>


    @GET("/api/d2/manage_products/getInActiveProducts")
    suspend fun getPremiumProducts(@Header("Authorization") authorization: String) : Response<PremiumProductsDTO>

    @GET("api/d2/member/dashboard/feed")
    suspend fun getJobsDetails(@Header("Authorization") authorization: String) : Response<JobsDTO>
}