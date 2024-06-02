package com.example.dynamicfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dynamicfragment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val favFragment = FavouritesFragment()
    private val dialFragment = DialerFragment()
    private val contactFragment = ContactsFragment()
    private val tag = "Log Messages"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(this, "onCreate Activity", Toast.LENGTH_SHORT).show()
        Log.i(tag, "onCreate Activity")

        try{
            addFragments()
            attachListener()
        }
        catch (e:java.lang.Exception){
            e.printStackTrace()
        }

    }

    private fun attachListener() {
        //Adding fragments on button click  (By calling function)
        binding.favourites.setOnClickListener{
            setCurrentFragment(favFragment)
        }
        binding.dialer.setOnClickListener{
            setCurrentFragment(dialFragment)
        }
        binding.contacts.setOnClickListener{
            setCurrentFragment(contactFragment)
        }

    }

    private fun addFragments() {
        // Adding fragment in main activity   (Direct)
//        val fragmentManager = supportFragmentManager.beginTransaction()
//        fragmentManager.replace(R.id.framelayout, DialerFragment())
//        fragmentManager.commit()

    }


    private fun setCurrentFragment(fragment : Fragment) {
        // fragment here takes the fragment which we need to apply.
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.framelayout, fragment)
//            add (R.id.framelayout, fragment)
            addToBackStack("Message to show")
            commit()
        }
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "onPause Activity", Toast.LENGTH_SHORT).show()
        Log.i(tag, "onPause Activity")
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "onStart Activity", Toast.LENGTH_SHORT).show()
        Log.i(tag, "onStart Activity ")

    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "onResume Activity", Toast.LENGTH_SHORT).show()
        Log.i(tag, "onResume Activity")
    }


    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "onStop Activity", Toast.LENGTH_SHORT).show()
        Log.i(tag, "onStop Activity")
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "onDestroy Activity", Toast.LENGTH_SHORT).show()
        Log.i(tag, "onDestroy Activity")
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this, "onRestart Activity", Toast.LENGTH_SHORT).show()
        Log.i(tag, "onRestart Activity")
    }
}