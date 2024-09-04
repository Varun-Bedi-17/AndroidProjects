package com.example.loanapplication.domain.use_cases

import com.example.loanapplication.data.model.UserAuthResponse
import com.example.loanapplication.domain.repositories.LoginRepo
import com.example.loanapplication.utils.ResponseState
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepo: LoginRepo
) {
    suspend operator fun invoke(username: String, password: String): ResponseState<UserAuthResponse> {
        return try {
            val result = loginRepo.loginFromRepo(username, password)
            if(result.message == "Sign In successful")
                ResponseState.Success(result)
            else
                ResponseState.Error(result.message)
        } catch (e: IOException) {
            ResponseState.Error("Network error: ${e.localizedMessage}")
        } catch (e: HttpException) {
            ResponseState.Error("HTTP error: ${e.localizedMessage}")
        } catch (e: Exception) {
            ResponseState.Error("Unexpected error: ${e.localizedMessage}")
        }
    }
}