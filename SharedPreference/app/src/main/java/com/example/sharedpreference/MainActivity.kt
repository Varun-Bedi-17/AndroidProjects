package com.example.sharedpreference

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler(Looper.getMainLooper()).postDelayed({
            val preference = getSharedPreferences("isLogin", MODE_PRIVATE)
            val isLogin = preference.getBoolean("flag", false)

            val intentForScreen  = if (isLogin){
                Intent(this, HomeScreen::class.java)
            }
            else {
                Intent(this, LoginScreen::class.java)
            }
            intentForScreen.putExtra("name", "Welcome from splash")
            startActivity(intentForScreen)
            finish()
        }
            ,
        4000)
    }
}