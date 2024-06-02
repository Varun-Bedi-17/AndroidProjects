package com.example.databinding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// This class is used for binding data with viewmodel
class ViewModelClass : ViewModel() {
    var liveNameDataObject = MutableLiveData<String>("Krishan")
    var liveAgeDataObject = MutableLiveData<Int>(18)


    fun getLiveData() {
        liveNameDataObject.value = "Mohit"
        liveAgeDataObject.value = 10
    }
}