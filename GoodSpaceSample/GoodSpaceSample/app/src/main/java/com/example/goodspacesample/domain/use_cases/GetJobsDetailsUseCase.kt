package com.example.goodspacesample.domain.use_cases

import com.example.goodspacesample.data.models.toDomain
import com.example.goodspacesample.domain.models.JobsModel
import com.example.goodspacesample.domain.repositories.GetJobsDetails
import com.example.goodspacesample.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetJobsDetailsUseCase @Inject constructor(
    private val getJobsDetails: GetJobsDetails
) {

    operator fun invoke(
        authorization: String
    ): Flow<ResponseState<List<JobsModel>>> = flow {
        try {
            emit(ResponseState.Loading())
            val response = getJobsDetails.getJobsDetailsFromRepo(authorization)
            if (response.isSuccessful) {
                val detailsList = response.body()?.data
                val jobDetailsList : MutableList<JobsModel> = mutableListOf()
                detailsList?.forEach {
                    if(it.type == "JOB")
                        jobDetailsList.add(it.toDomain())
                }
                emit(ResponseState.Success(data = jobDetailsList))
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