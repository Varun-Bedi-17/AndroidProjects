package com.example.service

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() , View.OnClickListener {
    private lateinit var btn1 : Button
    private lateinit var btn2 : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1 = findViewById<Button>(R.id.btn1) as Button
        btn2 = findViewById<Button>(R.id.btn2) as Button

        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
    }

    override fun onClick(button : View?) {
        if (button == btn1){
            startService(Intent(this, FirstService::class.java))
        }
        else if(button == btn2){
            stopService(Intent(this, FirstService::class.java))
        }
    }
}