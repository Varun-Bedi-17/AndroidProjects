package com.example.goodspacesample.presentation.views.get_otp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.goodspacesample.BuildConfig
import com.example.goodspacesample.R
import com.example.goodspacesample.data.models.LoginRequest
import com.example.goodspacesample.databinding.ActivityMainBinding
import com.example.goodspacesample.presentation.views.verify_otp.VerifyOtpFragment
import com.example.goodspacesample.util.Constants
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var getOtpImageSliderAdapter: GetOtpImageSliderAdapter
    private var dotsImage: Array<ImageView> = emptyArray()
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private var countryCode = ""
    private var isFragmentShowing = false
    private var isEditTextShowingError = false



    private val drawableList = listOf(R.drawable.explore_more_img,
        R.drawable.dream_drive_img,
        R.drawable.work_opportunities_img)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set status bar color
        window.statusBarColor = getColor(R.color.img_background)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.viewModel = mainActivityViewModel
        setContentView(binding.root)

        addListeners()
        setUpUIComponent()
        // Call the function only if it's not an orientation change
        retrievePhoneNumber()


        // Collecting getOtpState value from view-model.
        lifecycleScope.launch {
            mainActivityViewModel.mutableGetOtpState.collect { state ->
                if(!isFragmentShowing) {
                    println("Activity $state")
                    binding.progressBar.visibility =
                        if (state.isLoading) View.VISIBLE else View.GONE
                    binding.btnGetOtp.visibility =
                        if (state.isLoading) View.INVISIBLE else View.VISIBLE
                    if (state.error.isNotEmpty()) {
                        binding.llInputText.setBackgroundResource(R.drawable.edit_text_error_bg)
                        isEditTextShowingError = true
                        Toast.makeText(this@MainActivity, state.error, Toast.LENGTH_SHORT).show()
                    }
                    if (state.data.message == getString(R.string.otp_sent_to_your_number)) {
                        isFragmentShowing = true
                        openVerifyOtpFragment(countryCode)
                    }
                }
            }
        }
    }

    private fun openVerifyOtpFragment(countryCode: String) {
        val verifyOtpFragment = VerifyOtpFragment.newInstance(countryCode)
        supportFragmentManager.beginTransaction().add(R.id.fragment_view, verifyOtpFragment)
            .commit()
    }

    private fun addListeners() {
        // Viewpager change listener
        pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                dotsImage.mapIndexed { index, imageView ->
                    if (position == index) imageView.setImageResource(R.drawable.active_dot)
                    else imageView.setImageResource(R.drawable.non_active_dot)
                }
            }

        }

        binding.vpImg.registerOnPageChangeCallback(pageChangeListener)


        // Change background if editText is showing error.
        binding.editText.addTextChangedListener {
            if(isEditTextShowingError) {
                binding.llInputText.setBackgroundResource(R.drawable.edit_text_bg)
                isEditTextShowingError = false
            }
        }

        binding.btnGetOtp.setOnClickListener {
            val mobileNumber = binding.editText.text.toString()
            countryCode = binding.ccp.selectedCountryCode
            sendOtp(mobileNumber, countryCode)
        }

    }

    private fun sendOtp(mobileNumber: String, countryCode: String) {
        val authorizationToken = BuildConfig.PHONE_AUTH
        val deviceId = UUID.randomUUID().toString()

        val loginRequest = LoginRequest(mobileNumber, countryCode)
        if (mainActivityViewModel.mobileNumber.isNotEmpty()) mainActivityViewModel.getOtpForLoginFromViewModel(
            authorizationToken,
            deviceId,
            loginRequest)
        else Toast.makeText(this@MainActivity,
            "Phone number can't be empty",
            Toast.LENGTH_SHORT).show()
    }

    private fun setUpUIComponent() {
        setUpViewPager()
        createDots()
        autoScrollImages()
        addSpannableStrings()
    }

    private fun setUpViewPager() {
        getOtpImageSliderAdapter = GetOtpImageSliderAdapter()
        binding.vpImg.adapter = getOtpImageSliderAdapter
        getOtpImageSliderAdapter.submitList(drawableList)
        // Start from middle
        binding.vpImg.currentItem = drawableList.size / 2
    }

    private fun createDots() {
        dotsImage = Array(drawableList.size) { ImageView(this) }
        dotsImage.forEach { imgView ->
            imgView.setImageResource(R.drawable.non_active_dot)

            // Add dots in linear layout
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT).apply { setMargins(8, 8, 8, 8) }
            binding.sliderDot.addView(imgView, params)
        }
        // Middle dot is selected by default
        dotsImage[drawableList.size / 2].setImageResource(R.drawable.active_dot)

    }

    private fun autoScrollImages() {
        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                delay(Constants.IMAGE_SCROLL_DELAY_TIME) // Adjust the delay as per your requirement

                val currentItem = binding.vpImg.currentItem
                val nextItem =
                    (currentItem + 1) % drawableList.size // To scroll to next element and first element when reaches last index.
                binding.vpImg.currentItem = nextItem
            }
        }
    }

    private fun addSpannableStrings() {
        val firstText = getString(R.string.please_enter_number)
        setColoredSpan(binding.tvPleaseEnterNumber,
            firstText,
            getString(R.string.goodspace),
            R.color.light_blue)

        val secondText = getString(R.string.you_will_receive_otp)
        setColoredSpan(binding.tvReceiveOtp,
            secondText,
            getString(R.string.four_digit_otp),
            R.color.light_blue)
    }

    private fun setColoredSpan(
        textView: TextView,
        originalText: String,
        keyword: String,
        colorResId: Int,
    ) {
        val startIndex = originalText.indexOf(keyword)
        val endIndex = startIndex + keyword.length
        val spannableString = SpannableString(originalText)

        // Set different colors for specific character ranges
        spannableString.setSpan(ForegroundColorSpan(getColor(colorResId)),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.text = spannableString
    }


    private fun retrievePhoneNumber() {

        // Request launcher
        val phoneNumberHintIntentResultLauncher: ActivityResultLauncher<IntentSenderRequest> =
            registerForActivityResult(
                ActivityResultContracts.StartIntentSenderForResult()
            ) { result ->
                try {
                    val phoneNumber =
                        Identity.getSignInClient(this).getPhoneNumberFromIntent(result.data)

                    println(phoneNumber.substring(2, 4))           // Country Code
                    println(phoneNumber.substring(4))    // Number

                    // Set number retrieved in edittext
                    mainActivityViewModel.mobileNumber = phoneNumber.substring(4)

                    // Sending otp
                    sendOtp(phoneNumber.substring(4), phoneNumber.substring(2, 4))
                } catch (e: Exception) {
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()

                }
            }

        // To request for available phone numbers
        val hintRequest = GetPhoneNumberHintIntentRequest.builder().build()
        println("Making request for available phone numbers")
        try {
            Identity.getSignInClient(this@MainActivity)
                .getPhoneNumberHintIntent(hintRequest)
                .addOnSuccessListener {
                    phoneNumberHintIntentResultLauncher.launch(
                        IntentSenderRequest.Builder(it.intentSender).build()
                    )
                }
                .addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.vpImg.unregisterOnPageChangeCallback(pageChangeListener)
    }
}