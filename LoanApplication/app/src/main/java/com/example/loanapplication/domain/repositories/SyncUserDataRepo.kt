package com.example.loanapplication.domain.repositories

import com.example.loanapplication.domain.models.CallLogs
import com.example.loanapplication.domain.models.Contacts
import com.example.loanapplication.domain.models.Messages

interface SyncUserDataRepo {
    suspend fun getContactsList() : List<Contacts>
    suspend fun getCallLogsList() : List<CallLogs>
    suspend fun getMessagesList() : List<Messages>
    suspend fun uploadData(contacts: List<Contacts>, messages: List<Messages>, callLogs: List<CallLogs>)
}