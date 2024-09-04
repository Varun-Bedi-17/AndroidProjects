package com.example.loanapplication.presentation.ui.fragments

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.loanapplication.databinding.FragmentLoginBinding
import com.example.loanapplication.presentation.ui.activites.HomeActivity
import com.example.loanapplication.presentation.viewmodels.LoginViewModel
import com.example.loanapplication.utils.EventObserver
import com.example.loanapplication.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater).apply {
            viewModel = this@LoginFragment.viewModel
        }
        observeViewModel()
        return binding.root
    }

    /**
     * Sets up the observer for LiveData from the ViewModel.
     * Handles different UI states based on the ViewModel's state.
     */
    private fun observeViewModel() {
        viewModel.loginResult.observe(viewLifecycleOwner, EventObserver { result ->
            when(result){
                is ResponseState.Error -> showErrorState(result.message.toString())
                is ResponseState.Loading -> showLoadingState()
                is ResponseState.Success -> navigateToHome()
            }
        })
    }

    /**
     * Displays the loading state by showing the progress bar
     * and hiding other UI elements.
     */
    private fun showLoadingState() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            btnSignIn.visibility = View.GONE
            tvDontHaveAccount.visibility = View.GONE
            tvSignUp.visibility = View.GONE
        }
    }

    /**
     * Navigates to the HomeActivity after a successful login.
     * Finishes the current activity to prevent going back to the login screen.
     */
    private fun navigateToHome() {
        startActivity(Intent(activity, HomeActivity::class.java))
        activity?.finish()
    }

    /**
     * Shows an error message and resets the UI to allow the user
     * to try logging in again.
     *
     * @param message The error message to display in a Toast.
     */
    private fun showErrorState(message: String) {
        binding.apply {
            progressBar.visibility = View.GONE
            btnSignIn.visibility = View.VISIBLE
            tvDontHaveAccount.visibility = View.VISIBLE
            tvSignUp.visibility = View.VISIBLE
        }
        resetUI()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Resets the UI to its initial state, making all relevant UI elements visible.
     */
    private fun resetUI() {
        binding.apply {
            progressBar.visibility = View.GONE
            btnSignIn.visibility = View.VISIBLE
            tvDontHaveAccount.visibility = View.VISIBLE
            tvSignUp.visibility = View.VISIBLE
        }
    }
}
