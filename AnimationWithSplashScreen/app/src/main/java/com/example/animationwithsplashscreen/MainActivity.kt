package com.example.animationwithsplashscreen

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.animation.AnimationUtils.*
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTranslate = findViewById<Button>(R.id.btn1)
        val btnRotate = findViewById<Button>(R.id.btn2)
        val btnAlpha = findViewById<Button>(R.id.btn3)
        val btnScale = findViewById<Button>(R.id.btn4)
        val textToShow = findViewById<TextView>(R.id.text)

        btnTranslate.setOnClickListener {
            AnimationUtils.loadAnimation(this,R.anim.translate).apply {
                textToShow.startAnimation(this)
            }
        }

        btnRotate.setOnClickListener {
            val  anim = AnimationUtils.loadAnimation(this,R.anim.rotate)
            textToShow.startAnimation(anim)
        }

        btnAlpha.setOnClickListener {
            AnimationUtils.loadAnimation(this,R.anim.alpha).apply {
                textToShow.startAnimation(this)
            }
        }

        btnScale.setOnClickListener {
            val  anim = AnimationUtils.loadAnimation(this,R.anim.scale)
            textToShow.startAnimation(anim)
        }



    }
}