package com.example.goodspacesample.data.repositories

import com.example.goodspacesample.data.models.JobsDTO
import com.example.goodspacesample.data.remote.api.ApiService
import com.example.goodspacesample.domain.repositories.GetJobsDetails
import retrofit2.Response
import javax.inject.Inject

class GetJobsDetailsRepoImpl @Inject constructor(private val apiService: ApiService) : GetJobsDetails{

    override suspend fun getJobsDetailsFromRepo(authorization: String): Response<JobsDTO> {
        return apiService.getJobsDetails(authorization)
    }
}