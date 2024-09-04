package com.example.loanapplication.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.loanapplication.domain.use_cases.SyncUserDataUseCase
import com.example.loanapplication.domain.use_cases.UploadUserDataUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncUserDataWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val fetchUserDataUseCase: SyncUserDataUseCase,
    private val uploadUserDataUseCase: UploadUserDataUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            println("Comes in do work")
            val (contacts, messages, callLogs) = fetchUserDataUseCase()
            uploadUserDataUseCase(contacts, messages, callLogs)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}

