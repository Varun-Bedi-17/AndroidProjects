package com.example.staticbroadcastreciever

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    private lateinit var sendDataButton: Button
    private lateinit var viewDataButton : Button
    private lateinit var img : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        img = findViewById(R.id.image)
        sendDataButton = findViewById(R.id.sendData)
        viewDataButton = findViewById(R.id.viewData)

//        val intent = Intent()
        val action = intent.action    // intent which we receiving (getIntent()) through other applications.
        val type = intent.type

        // Static Method
        if (action == Intent.ACTION_SEND && type != null){
            img.setImageURI(intent.getParcelableExtra(Intent.EXTRA_STREAM))
        }

        sendDataButton.setOnClickListener {
            // Dynamica Method
            val intentForSendButton = Intent(Intent.ACTION_SEND)
//            intentForSendButton.type = "image/*"
//            intentForSendButton.putExtra("image", R.id.image)
            intentForSendButton.type = "text/plain"
            intentForSendButton.putExtra(Intent.EXTRA_EMAIL, "niranjantest@gmail.com")
            intentForSendButton.putExtra(Intent.EXTRA_SUBJECT, "This is a dummy message")
            intentForSendButton.putExtra(Intent.EXTRA_TEXT, "Dummy test message")
            startActivity(intentForSendButton)
        }
        viewDataButton.setOnClickListener {
            val intentForViewButton = Intent(Intent.ACTION_VIEW)
            startActivity(intentForViewButton)
        }
    }
}