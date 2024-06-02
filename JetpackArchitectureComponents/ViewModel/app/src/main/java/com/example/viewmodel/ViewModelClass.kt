package com.example.viewmodel

import androidx.lifecycle.ViewModel

class ViewModelClass(val initialNumber : Int) : ViewModel() {
    var number = initialNumber
    fun increment(){
        number++
    }
}