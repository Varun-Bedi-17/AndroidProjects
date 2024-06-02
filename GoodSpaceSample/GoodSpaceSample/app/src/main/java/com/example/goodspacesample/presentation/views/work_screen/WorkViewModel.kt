package com.example.goodspacesample.presentation.views.work_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goodspacesample.domain.use_cases.GetJobsDetailsUseCase
import com.example.goodspacesample.domain.use_cases.GetPremiumProductsUseCase
import com.example.goodspacesample.util.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class WorkViewModel @Inject constructor(private val getPremiumProductsUseCase: GetPremiumProductsUseCase, private val getJobsDetailsUseCase: GetJobsDetailsUseCase) : ViewModel() {

    private val _premiumProductsList = MutableStateFlow(PremiumProductsState())
    val premiumProductsList = _premiumProductsList

    private val _jobsDetailsList = MutableStateFlow(JobsDetailsState())
    val jobsDetailsList = _jobsDetailsList

    fun getPremiumProductsFromViewModel(authorization: String){
        getPremiumProductsUseCase(authorization).onEach {
            when (it) {
                is ResponseState.Loading -> {
                    _premiumProductsList.value = PremiumProductsState(isLoading = true)
                }
                is ResponseState.Error -> {
                    _premiumProductsList.value = PremiumProductsState(error = it.message ?: "Something unexpected occur")
                }
                is ResponseState.Success -> {
                    if (it.data != null)
                        _premiumProductsList.value = PremiumProductsState(data = it.data)
                    else
                        _premiumProductsList.value = PremiumProductsState(error = "Empty List")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getJobsDetailsFromViewModel(authorization: String){
        getJobsDetailsUseCase(authorization).onEach {
            when (it) {
                is ResponseState.Loading -> {
                    _jobsDetailsList.value = JobsDetailsState(isLoading = true)
                }
                is ResponseState.Error -> {
                    _jobsDetailsList.value = JobsDetailsState(error = it.message ?: "Something unexpected occur")
                }
                is ResponseState.Success -> {
                    if (it.data != null)
                        _jobsDetailsList.value = JobsDetailsState(data = it.data)
                    else
                        _jobsDetailsList.value = JobsDetailsState(error = "Empty List")
                }
            }
        }.launchIn(viewModelScope)
    }
}