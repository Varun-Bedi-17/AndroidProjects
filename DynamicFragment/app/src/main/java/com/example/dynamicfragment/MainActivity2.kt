package com.example.dynamicfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class MainActivity2 : AppCompatActivity() {
    private val tag = "Activity 2"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        Toast.makeText(this, "onCreate Activity 2", Toast.LENGTH_SHORT).show()
        Log.i(tag, "onCreate Activity 2")
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "onPause Activity 2", Toast.LENGTH_SHORT).show()
        Log.i(tag, "onPause Activity 2")
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "onStart Activity 2", Toast.LENGTH_SHORT).show()
        Log.i(tag, "onStart Activity 2")

    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "onResume Activity 2", Toast.LENGTH_SHORT).show()
        Log.i(tag, "onResume Activity 2")
    }



    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "onStop Activity 2", Toast.LENGTH_SHORT).show()
        Log.i(tag, "onStop Activity 2")
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "onDestroy Activity 2", Toast.LENGTH_SHORT).show()
        Log.i(tag, "onDestroy Activity 2")
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this, "onRestart Activity 2", Toast.LENGTH_SHORT).show()
        Log.i(tag, "onRestart Activity 2")
    }
}