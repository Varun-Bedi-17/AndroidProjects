package com.example.loanapplication.domain.use_cases

import com.example.loanapplication.domain.models.CallLogs
import com.example.loanapplication.domain.models.Contacts
import com.example.loanapplication.domain.models.Messages
import com.example.loanapplication.domain.repositories.SyncUserDataRepo
import javax.inject.Inject

class UploadUserDataUseCase @Inject constructor(private val syncUserDataRepo: SyncUserDataRepo) {
    suspend operator fun invoke(contacts: List<Contacts>, messages: List<Messages>, callLogs: List<CallLogs>) {
        syncUserDataRepo.uploadData(contacts, messages, callLogs)
    }
}