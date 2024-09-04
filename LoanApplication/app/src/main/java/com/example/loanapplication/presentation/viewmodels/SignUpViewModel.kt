package com.example.loanapplication.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loanapplication.data.model.UserAuthResponse
import com.example.loanapplication.domain.use_cases.SignupUseCase
import com.example.loanapplication.utils.Event
import com.example.loanapplication.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpUseCase: SignupUseCase) : ViewModel() {
    var mobile = ""
    var username = ""
    var password = ""

    private val _signUpResult = MutableLiveData<Event<ResponseState<UserAuthResponse>>>()
    val signUpResult: LiveData<Event<ResponseState<UserAuthResponse>>> = _signUpResult


    fun onSignUpClicked(){
        if (mobile.isBlank() || username.isBlank() || password.isBlank()) {
            _signUpResult.value = Event(ResponseState.Error("All fields must be filled"))
        } else {
            _signUpResult.value = Event(ResponseState.Loading(UserAuthResponse("Request Permissions")))
        }
    }


    fun signUp() {
        viewModelScope.launch {
            _signUpResult.value = Event(ResponseState.Loading())
            val result = signUpUseCase(mobile, username, password)
            _signUpResult.value = Event(result)
        }
    }
}