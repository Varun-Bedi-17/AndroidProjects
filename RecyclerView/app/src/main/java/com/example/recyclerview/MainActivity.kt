package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.recyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerContact.layoutManager = LinearLayoutManager(this)

        addAdapter()


    }

    private fun addAdapter() {
        val contactArray : ArrayList<ContactModel> = arrayListOf<ContactModel>()
        contactArray.add(ContactModel(R.drawable.a, "Varun", "987678092"))
        contactArray.add(ContactModel(R.drawable.b, "Aarush", "987678092"))
        contactArray.add(ContactModel(R.drawable.c, "Kajal", "987678092"))
        contactArray.add(ContactModel(R.drawable.d, "Kiran", "987678092"))
        contactArray.add(ContactModel(R.drawable.e, "Piyush", "987678092"))
        contactArray.add(ContactModel(R.drawable.f, "Rohan", "987678092"))
        contactArray.add(ContactModel(R.drawable.b, "Pranjal", "987678092"))
        contactArray.add(ContactModel(R.drawable.a, "Vedant", "987678092"))
        contactArray.add(ContactModel(R.drawable.e, "Sarthak", "987678092"))
        contactArray.add(ContactModel(R.drawable.c, "Raghav", "987678092"))

        val adapter = RecyclerContactAdapter(contactArray)
        binding.recyclerContact.adapter = adapter
    }

}