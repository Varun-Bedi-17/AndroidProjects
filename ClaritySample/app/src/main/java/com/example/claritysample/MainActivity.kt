package com.example.claritysample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.microsoft.clarity.Clarity
import com.microsoft.clarity.ClarityConfig

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val config = ClarityConfig("kvchetopnk")
        Clarity.initialize(applicationContext, config)
    }
}