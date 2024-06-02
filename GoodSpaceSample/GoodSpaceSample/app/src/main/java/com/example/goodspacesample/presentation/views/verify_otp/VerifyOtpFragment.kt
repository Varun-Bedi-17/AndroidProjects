package com.example.goodspacesample.presentation.views.verify_otp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import com.example.goodspacesample.BuildConfig
import com.example.goodspacesample.R
import com.example.goodspacesample.data.models.LoginRequest
import com.example.goodspacesample.data.models.VerifyRequest
import com.example.goodspacesample.databinding.FragmentVerifyOtpBinding
import com.example.goodspacesample.presentation.views.get_otp.MainActivityViewModel
import com.example.goodspacesample.presentation.views.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class VerifyOtpFragment : Fragment(), EditPhoneNumberDialog.OnGetOtpListener {

    private lateinit var binding: FragmentVerifyOtpBinding
    private var editPhoneNumberDialog: EditPhoneNumberDialog? = null
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    private var countryCode = ""

    companion object {
        const val CC_KEY = "CC_KEY"
        fun newInstance(
            countryCode: String,
        ): VerifyOtpFragment {
            val args = Bundle()

            args.putString(CC_KEY, countryCode)

            val fragment = VerifyOtpFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        countryCode = arguments?.getString(CC_KEY) ?: ""

        mainActivityViewModel.otpSentText = "OTP sent to +$countryCode "
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentVerifyOtpBinding.inflate(inflater)
        binding.viewModel = mainActivityViewModel
        addListeners()
        observeMutableStates()

        return binding.root
    }

    private fun addListeners() {

        binding.clTopBar.setOnClickListener {
            if (editPhoneNumberDialog == null || !editPhoneNumberDialog!!.isVisible) {
                editPhoneNumberDialog = EditPhoneNumberDialog.newInstance(this)
                editPhoneNumberDialog?.show(parentFragmentManager, null)
            }
        }

        binding.btnVerifyOtp.setOnClickListener {
            val userEnteredOtp = binding.etOtp.getText()
            if (userEnteredOtp.length != 4)
                Toast.makeText(activity,
                    "Please enter all four digits of the OTP to verify.",
                    Toast.LENGTH_SHORT).show()
            else
                mainActivityViewModel.verifyOtpForLoginFromViewModel(BuildConfig.PHONE_AUTH,
                    UUID.randomUUID().toString(),
                    VerifyRequest(mainActivityViewModel.mobileNumber, userEnteredOtp))
        }
    }

    private fun observeMutableStates() {
        lifecycle.coroutineScope.launch {
            mainActivityViewModel.mutableGetOtpState.collect { state ->
                println("Fragment $state")
                editPhoneNumberDialog?.updateViewsVisibility(state.isLoading)
                if (state.error.isNotEmpty()) {
                    Toast.makeText(activity, state.error, Toast.LENGTH_SHORT).show()
                }
                if (state.data.message == getString(R.string.otp_sent_to_your_number)) {
                    editPhoneNumberDialog?.dismiss()
                }
            }
        }

        lifecycle.coroutineScope.launch {
            mainActivityViewModel.mutableVerifyOtpState.collect { state ->
                editPhoneNumberDialog?.updateViewsVisibility(state.isLoading)
                if (state.error.isNotEmpty()) {
                    Toast.makeText(activity, state.error, Toast.LENGTH_SHORT).show()
                }
                if (state.data.message == getString(R.string.your_otp_has_been_verified)) {
                    editPhoneNumberDialog?.dismiss()
                    openHomeActivity()
                }

            }
        }
    }

    private fun openHomeActivity() {
        activity.let {
            startActivity(Intent(it, HomeActivity::class.java))
            it?.finish()
        }
    }

    override fun onGetOtpBtnCallback(countryCode: String, mobileNumber: String) {
        val authorizationToken = BuildConfig.PHONE_AUTH
        val deviceId = UUID.randomUUID().toString()
        val loginRequest = LoginRequest(mobileNumber, countryCode)
        if (mobileNumber.trim().isNotEmpty()) {
            mainActivityViewModel.mobileNumber = mobileNumber.trim()
            mainActivityViewModel.getOtpForLoginFromViewModel(authorizationToken,
                deviceId,
                loginRequest)
        } else Toast.makeText(activity, "Phone number can't be empty", Toast.LENGTH_SHORT).show()
    }


}