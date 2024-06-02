package com.example.lifecycleaware

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val tag = "Checking app lifecycle"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        To add observer of lifecycle
        lifecycle.addObserver(ObserverLifecycleEvent())
        lifecycle.addObserver(ActivityObserver())

        Toast.makeText(this, "onCreate Activity 1", Toast.LENGTH_SHORT).show()
        Log.i(tag, "onCreate Activity 1")
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "onPause Activity 1", Toast.LENGTH_SHORT).show()
        Log.i(tag, "onPause Activity 1")
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "onStart Activity 1", Toast.LENGTH_SHORT).show()
        Log.i(tag, "onStart Activity 1")

    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "onResume Activity 1", Toast.LENGTH_SHORT).show()
        Log.i(tag, "onResume Activity 1")
    }



    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "onStop Activity 1", Toast.LENGTH_SHORT).show()
        Log.i(tag, "onStop Activity 1")
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "onDestroy Activity 1", Toast.LENGTH_SHORT).show()
        Log.i(tag, "onDestroy Activity 1")
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this, "onRestart Activity 1", Toast.LENGTH_SHORT).show()
        Log.i(tag, "onRestart Activity 1")
    }
}