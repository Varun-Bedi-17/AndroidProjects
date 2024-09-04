package com.example.loanapplication.presentation.ui.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.loanapplication.databinding.FragmentLoanBinding
import com.example.loanapplication.presentation.viewmodels.LoanViewModel
import com.example.loanapplication.workmanager.SyncUserDataWorker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoanFragment : Fragment() {
    private lateinit var binding: FragmentLoanBinding
    private val viewModel: LoanViewModel by viewModels()

    @Inject
    lateinit var workManager : WorkManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoanBinding.inflate(inflater)

        uploadUserData()
        viewModel.fetchAndUploadUserData()
        return binding.root
    }

    private fun uploadUserData() {
        val syncUserDataRequest = OneTimeWorkRequestBuilder<SyncUserDataWorker>().build()
        workManager.enqueue(syncUserDataRequest)

        // Observe the work status
        workManager.getWorkInfoByIdLiveData(syncUserDataRequest.id)
            .observe(viewLifecycleOwner) { workInfo ->
                if (workInfo != null) {
                    when (workInfo.state) {
                        WorkInfo.State.ENQUEUED -> {
                            println("ENQUEUED")
                            // Work is enqueued
                        }

                        WorkInfo.State.RUNNING -> {
                            // Work is running
                            println("RUNNING")

                        }

                        WorkInfo.State.SUCCEEDED -> {
                            // Work completed successfully
                            println("SUCCEEDED")

                        }

                        WorkInfo.State.FAILED -> {
                            // Work failed
                            println("FAILED")

                        }

                        WorkInfo.State.BLOCKED -> {
                            // Work is blocked
                            println("BLOCKED")

                        }

                        WorkInfo.State.CANCELLED -> {
                            // Work is canceled
                            println("CANCELLED")

                        }
                    }
                }
            }
    }
}