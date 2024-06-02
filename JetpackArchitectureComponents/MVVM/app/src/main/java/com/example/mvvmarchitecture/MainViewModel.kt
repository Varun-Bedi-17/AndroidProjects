package com.example.mvvmarchitecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val quoteRepository: QuoteRepository) : ViewModel(){

    fun getQuotesFromViewModel() : LiveData<List<QuotesEntity2>>{
        return quoteRepository.getQuoteFromRepo()
    }

    fun insertQuoteFromViewModel(quote : QuotesEntity2){
        viewModelScope.launch(Dispatchers.IO){
            quoteRepository.insertQuoteFromRepo(quote)
        }
    }

}