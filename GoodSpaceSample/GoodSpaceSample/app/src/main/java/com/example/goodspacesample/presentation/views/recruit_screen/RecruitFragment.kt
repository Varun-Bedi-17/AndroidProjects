package com.example.goodspacesample.presentation.views.recruit_screen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.goodspacesample.R

class RecruitFragment : Fragment() {

    companion object {
        fun newInstance() = RecruitFragment()
    }

    private lateinit var viewModel: RecruitViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_recruit, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecruitViewModel::class.java)
        // TODO: Use the ViewModel
    }

}