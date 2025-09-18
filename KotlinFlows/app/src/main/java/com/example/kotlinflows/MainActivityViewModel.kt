package com.example.kotlinflows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    private val _livedata = MutableLiveData<String>()
    val livedata : LiveData<String> = _livedata
    fun changeLiveData(message: String){
        _livedata.value = message
    }

    private val _singleLiveEvent = SingleLiveEvent<String>()
    fun getSingleLiveEvent(): SingleLiveEvent<String> {
        return _singleLiveEvent
    }
    fun changeSingleLiveEvent(message: String){
        _singleLiveEvent.value = message
    }

}