package com.example.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelClass : ViewModel() {
    private val liveDataObject = MutableLiveData<String>("First Sentence")

    val liveData : LiveData<String>
    get() = liveDataObject


    fun updateLiveData(){
        liveDataObject.value = "Second Sentence"
    }
}