package com.example.mvvmarchitecture

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuotesDao {

    @Insert
    suspend fun insertQuoteFromDao(quote  : QuotesEntity)

    @Query("Select * from quotes")
    fun getQuotesFromDao() : LiveData<List<QuotesEntity>>
}