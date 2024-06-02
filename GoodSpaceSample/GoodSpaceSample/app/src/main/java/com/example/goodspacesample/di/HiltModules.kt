package com.example.goodspacesample.di

import com.example.goodspacesample.data.remote.api.ApiService
import com.example.goodspacesample.data.repositories.GetJobsDetailsRepoImpl
import com.example.goodspacesample.data.repositories.GetOtpForLoginRepoImpl
import com.example.goodspacesample.data.repositories.GetPremiumProductsRepoImpl
import com.example.goodspacesample.data.repositories.VerifyOtpForLoginRepoImpl
import com.example.goodspacesample.domain.repositories.GetJobsDetails
import com.example.goodspacesample.domain.repositories.GetOtpForLoginRepo
import com.example.goodspacesample.domain.repositories.GetPremiumProducts
import com.example.goodspacesample.domain.repositories.VerifyOtpRepo
import com.example.goodspacesample.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HiltModules {

    @Provides
    @Singleton
    fun provideApiService(): ApiService =
        Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)



    @Provides
    fun provideGetOtpForLoginRepo(apiService: ApiService) : GetOtpForLoginRepo =
        GetOtpForLoginRepoImpl(apiService)

    @Provides
    fun provideVerifyOtpForLoginRepo(apiService: ApiService) : VerifyOtpRepo =
        VerifyOtpForLoginRepoImpl(apiService)

    @Provides
    fun providePremiumProductRepo(apiService: ApiService) : GetPremiumProducts =
        GetPremiumProductsRepoImpl(apiService)

    @Provides
    fun provideJobDetailsRepo(apiService: ApiService) : GetJobsDetails =
        GetJobsDetailsRepoImpl(apiService)
}