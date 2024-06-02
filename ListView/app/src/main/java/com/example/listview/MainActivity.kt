package com.example.listview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.example.listview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        try{
            addListView()
            attachItemListener()
        }
        catch (e : Exception){
            e.printStackTrace()
        }

    }

    private fun attachItemListener() {
        binding.listView.setOnItemClickListener { adapterView, view, position, l ->
            val obj = BlankFragment()
            // Passing data from activity to fragment
            val bundle = Bundle()
            bundle.putString("name", adapterView.getItemAtPosition(position).toString())
            obj.arguments = bundle


            val frag = supportFragmentManager.beginTransaction()
            frag.replace(R.id.frameLayout, obj)
            frag.addToBackStack("Adding backstack")
            frag.commit()
        }
    }

    private fun addListView() {
        val arr = arrayListOf<String>("Varun", "Vikas", "Vikram", "Vijay", "Viyom")
        arr.add("Varun")
        arr.add("Varun")
        arr.add("Varun")
        arr.add("Varun")
        arr.add("Varun")
        arr.add("Varun")
        arr.add("Varun")
        arr.add("Varun")
        arr.add("Varun")
        arr.add("Varun")
        arr.add("Varun")

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, arr)
        binding.listView.adapter = adapter
    }
}