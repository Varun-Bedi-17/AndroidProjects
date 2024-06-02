package com.example.goodspacesample.data.repositories

import com.example.goodspacesample.data.models.PremiumProductsDTO
import com.example.goodspacesample.data.remote.api.ApiService
import com.example.goodspacesample.domain.repositories.GetPremiumProducts
import retrofit2.Response
import javax.inject.Inject

class GetPremiumProductsRepoImpl @Inject constructor(private val apiService: ApiService) : GetPremiumProducts{
    override suspend fun getPremiumProductsFromRepo(authorization: String): Response<PremiumProductsDTO> =
        apiService.getPremiumProducts(authorization)
}