package com.example.mvvmarchitecture

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class QuotesEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int,

    val text : String,
    val author : String
)