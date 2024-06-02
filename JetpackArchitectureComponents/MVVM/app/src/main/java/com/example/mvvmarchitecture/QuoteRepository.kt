package com.example.mvvmarchitecture

import androidx.lifecycle.LiveData

class QuoteRepository(private val quoteDao : QuotesDao2) {

    fun getQuoteFromRepo() : LiveData<List<QuotesEntity2>>{
        return quoteDao.getQuotesFromDao()
    }

    suspend fun insertQuoteFromRepo(quote : QuotesEntity2){
        return quoteDao.insertQuoteFromDao(quote)
    }
}