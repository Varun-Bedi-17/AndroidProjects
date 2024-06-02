package com.example.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    lateinit var viewModel : ViewModelClass
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val counter = findViewById<TextView>(R.id.counter)
        val btn = findViewById<Button>(R.id.btn)


        // Provide data to ViewModelClass when our class doesn't have any parameter
        //        viewModel = ViewModelProvider(this).get(ViewModelClass::class.java)

        // Provide data to ViewModelClass when our class have any parameter
        viewModel = ViewModelProvider(this, ViewModelFactory(10)).get(ViewModelClass::class.java)


        // Set text every time when activity create
        counter.text = viewModel.number.toString()


        btn.setOnClickListener {
            viewModel.increment()        // To call increment function of ViewModelClass
            counter.text = viewModel.number.toString()
        }
    }



}