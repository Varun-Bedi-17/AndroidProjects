package com.example.loanapplication.presentation.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.example.loanapplication.R
import com.example.loanapplication.data.model.UserAuthResponse
import com.example.loanapplication.domain.use_cases.LoginUseCase
import com.example.loanapplication.utils.Event
import com.example.loanapplication.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    var username = ""
    var password = ""

    private val _loginResult = MutableLiveData<Event<ResponseState<UserAuthResponse>>>()
    val loginResult: LiveData<Event<ResponseState<UserAuthResponse>>> = _loginResult


    fun onSignUpClicked(view: View){
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signupFragment)
    }

    fun onSignInClicked(){
        if (username.isBlank() || password.isBlank()) {
            _loginResult.value = Event(ResponseState.Error("Username or password can't be empty"))
        } else {
            login(username, password)
        }
    }


    private fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = Event(ResponseState.Loading())
            val result = loginUseCase(username, password)
            _loginResult.value = Event(result)
        }
    }
}