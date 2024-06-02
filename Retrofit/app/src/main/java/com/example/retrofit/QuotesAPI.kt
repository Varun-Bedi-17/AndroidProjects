package com.example.retrofit

import com.example.retrofit.modals.TokenModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface QuotesAPI  {
    @Headers(
        "Authorization:Basic Y2Y0Zjc5ZWI5YTNiNGZkYzkwOWU2NzdkZGY3YmI0Mjc6OGQ2MDhmZTM4MTgxNGVhYWFlYTM4MTIyOWY5N2UwY2Q=",
        "Content-Type:application/x-www-form-urlencoded"
    )
    @FormUrlEncoded
    @POST("token")
    suspend fun getAccessToken(@Field("grant_type") grantType: String) : Call<TokenModel>

    @Headers(
        "Authorization:Basic Y2Y0Zjc5ZWI5YTNiNGZkYzkwOWU2NzdkZGY3YmI0Mjc6OGQ2MDhmZTM4MTgxNGVhYWFlYTM4MTIyOWY5N2UwY2Q=",
        "Content-Type:application/x-www-form-urlencoded"
    )
    @FormUrlEncoded
    @POST("token")
    suspend fun refreshAccessToken(
        @Field("grant_type") grantType: String = "refresh_token",
        @Field("refresh_token") refreshToken: String
    ): Response<AccessTokenResponse>
}