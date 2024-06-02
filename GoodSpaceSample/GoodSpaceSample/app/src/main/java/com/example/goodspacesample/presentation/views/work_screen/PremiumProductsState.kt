package com.example.goodspacesample.presentation.views.work_screen

import com.example.goodspacesample.domain.models.PremiumProductModel

data class PremiumProductsState(
    val isLoading : Boolean = false,
    val error : String = "",
    val data : List<PremiumProductModel> = emptyList()
)
