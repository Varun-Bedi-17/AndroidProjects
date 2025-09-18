package com.example.constraintlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class BaseTextField : AppCompatActivity() {
    private lateinit var textInputLayout: TextInputLayout
    private lateinit var textInputEditText: TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_text_field)
        textInputLayout = findViewById(R.id.text_input_layout)
        textInputEditText = findViewById(R.id.text_input_edit_text)
    }



}