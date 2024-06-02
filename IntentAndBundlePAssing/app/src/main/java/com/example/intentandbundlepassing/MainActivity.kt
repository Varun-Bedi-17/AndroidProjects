package com.example.intentandbundlepassing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.app.Person
import com.example.intentandbundlepassing.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.button.setOnClickListener {
            val name = binding.name.text.toString()
            val age = binding.age.text.toString().toInt()
            val roll =  binding.roll.text.toString().toInt()

            val person = CheckIntentPass(name, age, roll)

            Intent(this, DataShow::class.java).also {
                it.putExtra("Name", name)
                it.putExtra("Age", age)
                it.putExtra("Roll",roll)

                // Sending all data at once using data class
                it.putExtra("person", person)
                startActivity(it)
            }



        }




    }
}
