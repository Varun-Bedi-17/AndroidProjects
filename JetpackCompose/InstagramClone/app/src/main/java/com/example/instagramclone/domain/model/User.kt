package com.example.instagramclone.domain.model

data class User(
    val name : String = "",
    val userName : String = "",
    val userId : String= "",
    val email : String = "",
    val password : String = "",
    val imageUrl : String = "",
    val following : List<String> = emptyList(),
    val followers : List<String> = emptyList(),
    val totalPosts : String = "",
    val bio : String = "",
)
