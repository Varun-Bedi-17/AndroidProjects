package com.example.intentandbundlepassing

import android.app.Person
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.example.intentandbundlepassing.databinding.ActivityDataShowBinding

class DataShow : AppCompatActivity() {
    private lateinit var binding2 : ActivityDataShowBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding2 = ActivityDataShowBinding.inflate(layoutInflater)
        setContentView(binding2.root)

        val name = intent.getStringExtra("Name")
        val age = intent.getIntExtra("Age", -1)
        val roll = intent.getIntExtra("Roll", -1)

        //getting through data class
        val person =  intent.getSerializableExtra("person") as CheckIntentPass

        binding2.setText.text = "Yor name is $name and age is $age and roll no is $roll and using data class ${person.toString()}"

        // To set title
        supportActionBar?.title = name
    }
}