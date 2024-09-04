package com.example.loanapplication.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.WorkManager
import com.example.loanapplication.domain.use_cases.SyncUserDataUseCase
import com.example.loanapplication.domain.use_cases.UploadUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoanViewModel @Inject constructor(
    // private val workManager: WorkManager,
    private val fetchUserDataUseCase: SyncUserDataUseCase,
    private val uploadUserDataUseCase: UploadUserDataUseCase
) : ViewModel() {

    val uploadResult = MutableLiveData<Result<Unit>>()
    val errorMessage = MutableLiveData<String>()

    fun fetchAndUploadUserData() {
        /*viewModelScope.launch {
            try {
                val (contacts, messages, callLogs) = fetchUserDataUseCase()
                if (contacts.isEmpty() && messages.isEmpty() && callLogs.isEmpty()) {
                    errorMessage.postValue("Failed to fetch user data.")
                    return@launch
                }
                uploadUserDataUseCase(contacts, messages, callLogs)
            } catch (e: Exception) {
                errorMessage.postValue("An unexpected error occurred: ${e.message}")
            }
        }*/

    }

}