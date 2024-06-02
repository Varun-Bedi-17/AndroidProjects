package com.first.launchmode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private var tag = "Function Calling in A: "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val b1 = findViewById<Button>(R.id.aA)
        val b2 = findViewById<Button>(R.id.aB)
        val b3 = findViewById<Button>(R.id.aC)
        val b4 = findViewById<Button>(R.id.aD)

        Log.i(tag, "onCreate()")
        b1.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
        b2.setOnClickListener {
            startActivity(Intent(this,Bactivity::class.java))
        }
        b3.setOnClickListener {
            startActivity(Intent(this,Cactivity::class.java))
        }
        b4.setOnClickListener {
            startActivity(Intent(this,Dactivity::class.java))
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.i(tag, "onNewIntent()")
    }
}