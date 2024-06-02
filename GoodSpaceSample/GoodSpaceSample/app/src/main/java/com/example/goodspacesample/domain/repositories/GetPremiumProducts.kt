package com.example.goodspacesample.domain.repositories

import com.example.goodspacesample.data.models.PremiumProductsDTO
import retrofit2.Response

interface GetPremiumProducts {
    suspend fun getPremiumProductsFromRepo(authorization: String) : Response<PremiumProductsDTO>
}