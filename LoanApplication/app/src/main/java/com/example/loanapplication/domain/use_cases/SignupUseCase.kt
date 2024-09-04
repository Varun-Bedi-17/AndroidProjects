package com.example.loanapplication.domain.use_cases

import com.example.loanapplication.data.model.UserAuthResponse
import com.example.loanapplication.domain.repositories.SignupRepo
import com.example.loanapplication.utils.ResponseState
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class SignupUseCase @Inject constructor(
    private val signupRepo: SignupRepo
) {
    suspend operator fun invoke(mobile: String, username: String, password: String): ResponseState<UserAuthResponse> {
        return try {
            val result = signupRepo.signUpFromRepo(mobile, username, password)
            if(result.message == "Sign Up successful")
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