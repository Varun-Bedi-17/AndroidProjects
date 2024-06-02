package com.example.sharedpreference

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        val logoutButton = findViewById<Button>(R.id.logoutButton)
        val msg = findViewById<TextView>(R.id.msg)

        val intent = intent
        val msgInt = intent.getStringExtra("name")
        msg.text = msgInt

        logoutButton.setOnClickListener {
            val preferenceForLogout = getSharedPreferences("isLogin", MODE_PRIVATE)
            val preferenceForLogoutEdit = preferenceForLogout.edit()

            preferenceForLogoutEdit.putBoolean("flag", false)
            preferenceForLogoutEdit.apply()

            startActivity(Intent(this,MainActivity::class.java))
            finish()

        }
    }

//    override fun onResume() {
//        val msg = findViewById<TextView>(R.id.msg)
//        val intent = intent
//        val msgInt = intent.getStringExtra("name")
//        msg.text = msgInt
//        super.onResume()
//    }
}