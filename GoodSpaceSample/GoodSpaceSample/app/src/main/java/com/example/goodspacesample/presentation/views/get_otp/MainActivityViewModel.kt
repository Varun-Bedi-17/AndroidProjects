package com.example.goodspacesample.presentation.views.get_otp


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodspacesample.data.models.LoginRequest
import com.example.goodspacesample.data.models.VerifyRequest
import com.example.goodspacesample.domain.use_cases.GetOtpForLoginUseCase
import com.example.goodspacesample.domain.use_cases.VerifyOtpForLoginUseCase
import com.example.goodspacesample.presentation.views.verify_otp.VerifyOtpForLoginState
import com.example.goodspacesample.util.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val getOtpForLoginUseCase: GetOtpForLoginUseCase, private val verifyOtpForLoginUseCase: VerifyOtpForLoginUseCase) : ViewModel() {

    var otpSentText = ""
    var mobileNumber = ""

    private val _mutableGetOtpState = MutableStateFlow(GetOtpForLoginState())
    val mutableGetOtpState = _mutableGetOtpState

    private val _mutableVerifyOtpState = MutableStateFlow(VerifyOtpForLoginState())
    val mutableVerifyOtpState = _mutableVerifyOtpState


    fun getOtpForLoginFromViewModel(
        authorization: String,
        deviceId: String,
        request: LoginRequest,
    ) {
        getOtpForLoginUseCase(authorization, deviceId, request).onEach {
            when (it) {
                is ResponseState.Loading -> {
                    _mutableGetOtpState.value = GetOtpForLoginState(isLoading = true)
                }
                is ResponseState.Error -> {
                    _mutableGetOtpState.value = GetOtpForLoginState(error = it.message ?: "Something unexpected occur")
                }
                is ResponseState.Success -> {
                    if (it.data != null)
                        _mutableGetOtpState.value = GetOtpForLoginState(data = it.data)
                    else
                        _mutableGetOtpState.value = GetOtpForLoginState(error = "Something unexpected occur")
                }
            }
        }.launchIn(viewModelScope)
    }


    fun verifyOtpForLoginFromViewModel(
        authorization: String,
        deviceId: String,
        request: VerifyRequest
    ) {
        verifyOtpForLoginUseCase(authorization, deviceId, request).onEach {
            when (it) {
                is ResponseState.Loading -> {
                    _mutableVerifyOtpState.value = VerifyOtpForLoginState(isLoading = true)
                }
                is ResponseState.Error -> {
                    _mutableVerifyOtpState.value = VerifyOtpForLoginState(error = it.message ?: "Something unexpected occur")
                }
                is ResponseState.Success -> {
                    if (it.data != null)
                        _mutableVerifyOtpState.value = VerifyOtpForLoginState(data = it.data)
                    else
                        _mutableVerifyOtpState.value = VerifyOtpForLoginState(error = "Something unexpected occur")
                }
            }
        }.launchIn(viewModelScope)
    }

}