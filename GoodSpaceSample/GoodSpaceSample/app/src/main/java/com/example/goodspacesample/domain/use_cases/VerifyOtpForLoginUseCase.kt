package com.example.goodspacesample.domain.use_cases

import com.example.goodspacesample.data.models.LoginResponse
import com.example.goodspacesample.data.models.VerifyRequest
import com.example.goodspacesample.domain.repositories.VerifyOtpRepo
import com.example.goodspacesample.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class VerifyOtpForLoginUseCase @Inject constructor(
    private val verifyOtpForLoginRepo: VerifyOtpRepo
) {

    operator fun invoke(
        authorization: String,
        deviceId: String,
        request: VerifyRequest
    ): Flow<ResponseState<LoginResponse>> = flow {
        try{
            emit(ResponseState.Loading())
            val response = verifyOtpForLoginRepo.verifyOtpForLoginFromRepo(authorization, deviceId, request)
            if(response.isSuccessful) response.body()?.let { ResponseState.Success(it) }
                ?.let { emit(it) }
            else {
                emit(ResponseState.Error(message = response.message(), data = null))
            }
        }
        catch (e : HttpException){
            emit(ResponseState.Error(message = e.localizedMessage ?: "Unknown Error"))
        }catch (e : IOException){
            emit(ResponseState.Error(message = e.localizedMessage ?: "Check your internet connection"))
        }catch(e : Exception){
            emit(ResponseState.Error(message = e.localizedMessage ?: "Something went wrong"))
        }
    }
}