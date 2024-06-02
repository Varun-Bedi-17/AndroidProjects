package com.example.mvvmarchitecture

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quote")
data class QuotesEntity2(
    @PrimaryKey(autoGenerate = true)
    val id : Int,

    val text : String,
    val author : String
)