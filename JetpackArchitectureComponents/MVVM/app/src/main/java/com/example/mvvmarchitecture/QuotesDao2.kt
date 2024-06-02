package com.example.mvvmarchitecture

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuotesDao2 {

    @Insert
    suspend fun insertQuoteFromDao(quote  : QuotesEntity2)

    @Query("Select * from quote")
    fun getQuotesFromDao() : LiveData<List<QuotesEntity2>>
}