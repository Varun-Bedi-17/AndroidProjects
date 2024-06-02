package com.example.relativelayout

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    lateinit var printTime: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            attachListener()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }


    }

    private fun attachListener() {
        timeSelectListener()
        btnListener()

    }

    private fun btnListener() {
        var bt1 = findViewById<Button>(R.id.button1) as Button
        var empId = findViewById<EditText>(R.id.input_id) as EditText
        var spinn = findViewById<Spinner>(R.id.spinner)
        bt1.setOnClickListener {
            var id = empId.text.toString()
            if (id == "25") {
                Toast.makeText(
                    applicationContext,
                    "Hi! your id is $id and time is ${printTime.text.toString()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        

        val aa = ArrayAdapter.createFromResource(
            this, R.array.Department, android.R.layout.simple_spinner_dropdown_item
        )
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinn.adapter = aa
    }


    private fun timeSelectListener() {
        var timeSelect = findViewById<Button>(R.id.timeButton)
        printTime = findViewById<TextView>(R.id.printTime)
        val timePicker = timeSelect.setOnClickListener() {
            val currentTime = Calendar.getInstance()
            val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)
            val currentMinute = currentTime.get(Calendar.MINUTE)
            TimePickerDialog(
                this,
                { view, hour, minute ->
                    printTime.setText("Your time is $hour:$minute")
                },
                currentHour,
                currentMinute,
                false
            ).show()
        }
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(applicationContext, "Hi! you are at onStart() ", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onResume() {
        super.onResume()
    }

}
