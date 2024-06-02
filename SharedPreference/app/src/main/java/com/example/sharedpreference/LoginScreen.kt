package com.example.sharedpreference

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val preferenceForLogin = getSharedPreferences("isLogin", MODE_PRIVATE)
            val preferenceForLoginEdit = preferenceForLogin.edit()

            preferenceForLoginEdit.putBoolean("flag", true)
            preferenceForLoginEdit.apply()

            val int = Intent(this, HomeScreen::class.java)
            val int2 = Intent(this, MainActivity::class.java)
            int2.putExtra("name", "Welcome from login")
            int.putExtra("name", "Welcome from login")
            startActivity(int)
            finish()
        }
    }
}