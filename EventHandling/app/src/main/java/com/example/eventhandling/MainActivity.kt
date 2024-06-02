package com.example.eventhandling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.eventhandling.databinding.ActivityMainBinding

// These View.OnLongClickListener etc are event listener
class MainActivity : AppCompatActivity(), View.OnClickListener, View.OnLongClickListener,
    View.OnTouchListener, View.OnCreateContextMenuListener {
    lateinit var binding:ActivityMainBinding
    val logTag = "Checking listeners"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            attachListener()
        }catch (e:java.lang.Exception){
            e.printStackTrace()
        }

    }

    private fun attachListener() {
        binding.buttonShow.setOnClickListener(this)
        binding.buttonShow.setOnLongClickListener(this)
//        binding.buttonShow.setOnTouchListener(this)


        // Another method for adding event.
        binding.focusChange.setOnFocusChangeListener { view, b ->
            if (b) {
                Toast.makeText(this, " onFocusChange()", Toast.LENGTH_SHORT).show()
                Log.i(logTag, "onFocusChange()")
                binding.showText.text = "onFocusChange() true"
            }
            else{
                binding.showText.text = "onFocusChange() false"
            }

        }
    }


    override fun onClick(a: View?) {                   // These are event handler.
        when(a)
        {
            binding.buttonShow->{
                Toast.makeText(this, " onClick()", Toast.LENGTH_SHORT).show()
                Log.i(logTag, "onClick()")
                binding.showText.text = "onClick() called"
            }
        }
    }

    override fun onLongClick(a1: View?): Boolean {
        when(a1)
        {
            binding.buttonShow->{
                Toast.makeText(this, " onLongClick()", Toast.LENGTH_SHORT).show()
                Log.i(logTag, "onLongClick()")
                binding.showText.text = "onLongClick() called"
            }
        }
        return false    // It means binding.showText changed to its previous state.
    }

    override fun onTouch(a3: View?, p1: MotionEvent?): Boolean {
        when(a3)
        {
            binding.buttonShow->{
                Toast.makeText(this, " onTouch()", Toast.LENGTH_SHORT).show()
                Log.i(logTag, "onTouch()")
                binding.showText.text = "onTouch() called"
            }
        }
        return false
    }


    // For menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.Item1 -> Toast.makeText(this, "Item 1 selected ", Toast.LENGTH_SHORT).show()
            R.id.Item2 -> Toast.makeText(this, "Item 2 selected ", Toast.LENGTH_SHORT).show()
            R.id.Item3 -> Toast.makeText(this, "Item 3 selected ", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

}