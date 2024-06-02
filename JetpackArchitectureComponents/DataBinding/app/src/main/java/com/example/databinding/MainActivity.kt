package com.example.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.databinding.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

// This code is for normal binding using data class user.
//        val userProfile = User("Varun", 17)
//        binding.user = userProfile

        val viewModel = ViewModelProvider(this).get(ViewModelClass::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.btn.setOnClickListener {
            Log.i("Checking", binding.name.text.toString())
            viewModel.getLiveData()
        }


    }
}
