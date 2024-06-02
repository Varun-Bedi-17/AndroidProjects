package com.example.goodspacesample.data.models

import com.example.goodspacesample.domain.models.PremiumProductModel

data class PremiumProductDTO(
    val displayName: String,
    val productName: String
)

fun PremiumProductDTO.toDomainModel() : PremiumProductModel{
    return PremiumProductModel(
        name = "Andrew",
        productName = this.displayName,
        imageUrl = null
    )
}