package com.example.goodspacesample.data.models

data class UserInfo(
    val image_id: String,
    val isProfileVerified: Boolean,
    val name: String,
    val score: Double,
    val user_id: Int
)