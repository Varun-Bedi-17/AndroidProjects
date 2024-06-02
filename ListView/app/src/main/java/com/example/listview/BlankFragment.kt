package com.example.listview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.listview.databinding.FragmentBlankBinding

class BlankFragment : Fragment() {
    private var _binding : FragmentBlankBinding? = null
    private val binding
    get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Fragment in binding
        _binding = FragmentBlankBinding.inflate(inflater, container, false)

        val argumentsFromActivity = arguments?.getString("name")
        binding.name.text = argumentsFromActivity


        return binding.root
    }

}