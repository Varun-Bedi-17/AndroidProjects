package com.example.goodspacesample.presentation.views.verify_otp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.goodspacesample.R
import com.example.goodspacesample.databinding.LayoutEditPhoneNumberDialogBinding

class EditPhoneNumberDialog : DialogFragment() {
    private lateinit var binding: LayoutEditPhoneNumberDialogBinding
    private var onGetOtpListener : OnGetOtpListener? = null
    companion object {

        fun newInstance(
            onGetOtpListener: OnGetOtpListener
        ): EditPhoneNumberDialog {

            val fragment = EditPhoneNumberDialog()
            fragment.onGetOtpListener = onGetOtpListener
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutEditPhoneNumberDialogBinding.inflate(inflater)

        dialog?.setCanceledOnTouchOutside(false)

        binding.btnGetOtp.setOnClickListener {
            val countryCode = binding.ccp.selectedCountryCode
            val mobileNumber = binding.editText.text.toString()
            onGetOtpListener?.onGetOtpBtnCallback(countryCode, mobileNumber)
        }

        return binding.root
    }

    fun updateViewsVisibility(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnGetOtp.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
    }

    interface OnGetOtpListener{
        fun onGetOtpBtnCallback(countryCode : String, mobileNumber : String)
    }
}