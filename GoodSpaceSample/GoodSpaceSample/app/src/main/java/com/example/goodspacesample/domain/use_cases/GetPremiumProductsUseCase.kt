package com.example.goodspacesample.domain.use_cases

import com.example.goodspacesample.data.models.toDomainModel
import com.example.goodspacesample.domain.models.PremiumProductModel
import com.example.goodspacesample.domain.repositories.GetPremiumProducts
import com.example.goodspacesample.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPremiumProductsUseCase @Inject constructor(
    private val getPremiumProductsRepo: GetPremiumProducts,
) {

    operator fun invoke(
        authorization: String
    ): Flow<ResponseState<List<PremiumProductModel>>> = flow {
        try {
            emit(ResponseState.Loading())
            val response = getPremiumProductsRepo.getPremiumProductsFromRepo(authorization)
            if (response.isSuccessful) {
                val premiumProductsList = response.body()?.data
                    ?.map { it.toDomainModel() }
                emit(ResponseState.Success(data = premiumProductsList ?: emptyList()))
            } else {
                emit(ResponseState.Error(message = response.message(), data = null))
            }
        } catch (e: HttpException) {
            emit(ResponseState.Error(message = e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(ResponseState.Error(message = e.localizedMessage
                ?: "Check your internet connection"))
        } catch (e: Exception) {
            emit(ResponseState.Error(message = e.localizedMessage ?: "Something went wrong"))
        }
    }
}