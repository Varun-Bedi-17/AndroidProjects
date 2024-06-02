package com.example.animationwithsplashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // This is used to hide the status bar and make the splash screen as a full screen activity.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val splashScreenImage = findViewById<ImageView>(R.id.splashScreenImage)
        val anim = AnimationUtils.loadAnimation(this, R.anim.alpha)
        splashScreenImage.startAnimation(anim)

        // Handler().postDelayed()
        Handler(Looper.getMainLooper()).postDelayed({
            Intent(this, MainActivity::class.java).also{
                startActivity(it)
                finish()
            }
        },
        5000)
    }
}