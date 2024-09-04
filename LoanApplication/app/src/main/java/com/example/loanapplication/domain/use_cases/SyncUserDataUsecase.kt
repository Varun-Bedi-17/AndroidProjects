package com.example.loanapplication.domain.use_cases

import com.example.loanapplication.domain.models.CallLogs
import com.example.loanapplication.domain.models.Contacts
import com.example.loanapplication.domain.models.Messages
import com.example.loanapplication.domain.repositories.SyncUserDataRepo
import javax.inject.Inject

class SyncUserDataUseCase @Inject constructor(
    private val syncUserDataRepo: SyncUserDataRepo
) {
    suspend operator fun invoke() : Triple<List<Contacts>, List<Messages>, List<CallLogs>> {
        val contacts = syncUserDataRepo.getContactsList()
        val messages = syncUserDataRepo.getMessagesList()
        val callLogs = syncUserDataRepo.getCallLogsList()

        return Triple(contacts, messages, callLogs)
    }
}