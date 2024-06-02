package com.example.goodspacesample.presentation.views.verify_otp

import com.example.goodspacesample.data.models.LoginResponse

data class VerifyOtpForLoginState(
    val isLoading : Boolean = false,
    val error : String = "",
    val data : LoginResponse = LoginResponse("")
)
