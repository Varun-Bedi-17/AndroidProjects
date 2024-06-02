package com.example.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Help {
//    val okHttpClient = OkHttpClient.Builder()
//        .addInterceptor { chain ->
//            val request = chain.request().newBuilder()
//                .addHeader("Authorization", "Basic Y2Y0Zjc5ZWI5YTNiNGZkYzkwOWU2NzdkZGY3YmI0Mjc6OGQ2MDhmZTM4MTgxNGVhYWFlYTM4MTIyOWY5N2UwY2Q=")
//                .build()
//            chain.proceed(request)
//        }
//        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://accounts.spotify.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}