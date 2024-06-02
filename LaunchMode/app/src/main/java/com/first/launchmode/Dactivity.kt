package com.first.launchmode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class Dactivity : AppCompatActivity() {
    private var tag = "Function Calling in D: "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dactivity)

        Log.i(tag, "onCreate()")

        val b2 = findViewById<Button>(R.id.dB)
        val b3 = findViewById<Button>(R.id.dC)
        val b4 = findViewById<Button>(R.id.dA)
        val b1 = findViewById<Button>(R.id.dD)
        b1.setOnClickListener {
            startActivity(Intent(this,Dactivity::class.java))
        }
        b2.setOnClickListener {
            startActivity(Intent(this,Bactivity::class.java))
        }
        b3.setOnClickListener {
            startActivity(Intent(this,Cactivity::class.java))
        }
        b4.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.i(tag, "onNewIntent()")
    }
}