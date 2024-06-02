package com.example.goodspacesample.domain.repositories

import com.example.goodspacesample.data.models.JobsDTO
import retrofit2.Response

interface GetJobsDetails {
    suspend fun getJobsDetailsFromRepo(authorization: String) : Response<JobsDTO>
}