package com.example.goodspacesample.presentation.views.work_screen

import com.example.goodspacesample.domain.models.JobsModel

data class JobsDetailsState(
    val isLoading : Boolean = false,
    val error : String = "",
    val data : List<JobsModel> = emptyList()
)
