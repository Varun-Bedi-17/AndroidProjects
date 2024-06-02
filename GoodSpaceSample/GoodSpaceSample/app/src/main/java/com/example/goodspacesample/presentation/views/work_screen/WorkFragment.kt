package com.example.goodspacesample.presentation.views.work_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goodspacesample.BuildConfig
import com.example.goodspacesample.databinding.FragmentWorkBinding
import com.example.goodspacesample.domain.models.JobsModel
import com.example.goodspacesample.domain.models.PremiumProductModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WorkFragment : Fragment() {

    private val viewModel: WorkViewModel by viewModels()
    private lateinit var binding: FragmentWorkBinding
    private val premiumProductsAdapter = PremiumProductsAdapter()
    private val jobDetailsAdapter = JobDetailsAdapter()
    private lateinit var premiumProductsList: List<PremiumProductModel>
    private lateinit var jobsDetailsList: List<JobsModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentWorkBinding.inflate(inflater)

        // Api cal to get premium products
        lifecycleScope.launch {
            viewModel.getPremiumProductsFromViewModel(BuildConfig.PHONE_AUTH)
            viewModel.getJobsDetailsFromViewModel(BuildConfig.PHONE_AUTH)
        }

        lifecycleScope.launch {
            viewModel.premiumProductsList.collect { state ->
                println(state)

                with(binding) {
                    clProgressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                    clTopBar.visibility = if (state.isLoading) View.INVISIBLE else View.VISIBLE

                    if (state.error.isNotEmpty()) {
                        rvPremiumProducts.visibility = View.GONE
                        tvNoPremiumProduct.visibility = View.VISIBLE

                        if (state.error != "Empty List") {
                            tvNoPremiumProduct.text = state.error
                        }
                        Toast.makeText(activity, state.error, Toast.LENGTH_SHORT).show()
                    }

                    state.data.takeIf { it.isNotEmpty() }?.let {
                        premiumProductsList = it
                        setUpPremiumProductsRv()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.jobsDetailsList.collect { state ->
                println(state)
                state.data.takeIf { it.isNotEmpty() }?.let {
                    jobsDetailsList = it
                    setUpJobDetailsAdapter()
                }
            }
        }


        binding.cvCircularAvatar.viewTreeObserver.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                // Remove the listener to avoid multiple calls
                binding.cvCircularAvatar.viewTreeObserver.removeOnPreDrawListener(this)

                // Calculate the corner radius as half of the CardView width
                val cornerRadius = binding.cvCircularAvatar.width / 2f

                // Set the calculated corner radius
                binding.cvCircularAvatar.radius = cornerRadius

                return true
            }
        })





        return binding.root
    }


    private fun setUpPremiumProductsRv() {
        binding.rvPremiumProducts.apply {
            layoutManager =
                LinearLayoutManager(
                    requireActivity(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            setHasFixedSize(true)
            premiumProductsAdapter.submitList(
                premiumProductsList
            )
            adapter = premiumProductsAdapter
        }
    }

    private fun setUpJobDetailsAdapter() {
        binding.rvJobs.apply {
            layoutManager =
                LinearLayoutManager(
                    requireActivity(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            setHasFixedSize(true)
            jobDetailsAdapter.submitList(
                jobsDetailsList
            )
            adapter = jobDetailsAdapter
        }
    }
}