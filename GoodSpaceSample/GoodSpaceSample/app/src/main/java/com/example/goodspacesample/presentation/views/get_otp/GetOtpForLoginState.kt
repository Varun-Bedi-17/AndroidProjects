package com.example.goodspacesample.presentation.views.get_otp

import com.example.goodspacesample.data.models.LoginResponse

data class GetOtpForLoginState(
    val isLoading : Boolean = false,
    val error : String = "",
    val data : LoginResponse = LoginResponse("")
)
