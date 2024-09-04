package com.example.loanapplication.di

import android.content.ContentResolver
import android.content.Context
import androidx.work.WorkManager
import com.example.loanapplication.data.repositories.LoginRepoImpl
import com.example.loanapplication.data.repositories.SignupRepoImpl
import com.example.loanapplication.data.repositories.SyncUserRepoImpl
import com.example.loanapplication.data.source.remote.ApiService
import com.example.loanapplication.domain.repositories.LoginRepo
import com.example.loanapplication.domain.repositories.SignupRepo
import com.example.loanapplication.domain.repositories.SyncUserDataRepo
import com.example.loanapplication.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @Singleton
    fun provideLoginRepository(
        apiService: ApiService
    ): LoginRepo {
        return LoginRepoImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideSignUpRepository(
        apiService: ApiService
    ): SignupRepo {
        return SignupRepoImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver {
        return context.contentResolver
    }

    @Provides
    @Singleton
    fun provideSyncUserDataRepository(
        apiService: ApiService,
        contentResolver: ContentResolver
    ): SyncUserDataRepo {
        return SyncUserRepoImpl(contentResolver, apiService)
    }

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }
}