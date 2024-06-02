package com.example.brodcastreciever

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private lateinit var reciever : AirplaneModeChanged
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reciever = AirplaneModeChanged()

        val intentFilter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)

        registerReceiver(reciever, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(reciever)
    }
}