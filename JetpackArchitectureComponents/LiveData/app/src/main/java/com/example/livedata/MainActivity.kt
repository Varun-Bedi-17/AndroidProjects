package com.example.livedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private val btn : Button
    get() = findViewById(R.id.btn)

    private val textInView : TextView
    get() = findViewById(R.id.text)

    private lateinit var viewModel : ViewModelClass
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(ViewModelClass::class.java)

        val observer = Observer<String>{
            textInView.text = it
        }
        viewModel.liveData.observe(this, observer)

        // Direct method
//        viewModel.liveData.observe(this,{
//            textInView.text = it          // This is a onChanged method for Observer object and we have
//        })                               //attached this function through observe() method which
                                          // takes context,Observer(object) as parameter.



        btn.setOnClickListener {
            viewModel.updateLiveData()
        }
    }
}