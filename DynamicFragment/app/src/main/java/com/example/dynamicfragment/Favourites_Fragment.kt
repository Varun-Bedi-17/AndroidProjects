package com.example.dynamicfragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

class FavouritesFragment : Fragment() {
    private val tagForFavFragment = "Log Fragment A"
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Toast.makeText(activity, " Fragment A on Attach ", Toast.LENGTH_LONG).show()
        Log.i(tagForFavFragment, " on Attach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(tagForFavFragment, " on Create")
        Toast.makeText(activity, " Fragment A on Create ", Toast.LENGTH_LONG).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Toast.makeText(activity, " Fragment A on Create View ", Toast.LENGTH_LONG).show()
        Log.i(tagForFavFragment, " on Create View ")

        val view = inflater.inflate(R.layout.fav_fragment, container, false)
        val btn = view.findViewById<Button>(R.id.btn)
        btn.setOnClickListener{
            startActivity(Intent(activity, MainActivity2::class.java))
        }
        return view

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(tagForFavFragment, " on View Created")
        Toast.makeText(activity, " Fragment A on View Created ", Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        Log.i(tagForFavFragment, " on Start")
        Toast.makeText(activity, " Fragment A on Start ", Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        Log.i(tagForFavFragment, " on Resume")
        Toast.makeText(activity, " Fragment A on Resume ", Toast.LENGTH_LONG).show()
    }

    override fun onPause() {
        super.onPause()
        Log.i(tagForFavFragment, " on Pause")
        Toast.makeText(activity, " Fragment A on Pause ", Toast.LENGTH_LONG).show()
    }

    override fun onStop() {
        super.onStop()
        Log.i(tagForFavFragment, " on Stop")
        Toast.makeText(activity, " Fragment A on Stop ", Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(tagForFavFragment, " on Destroy View ")
        Toast.makeText(activity, " Fragment A on Destroy View ", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(tagForFavFragment, " on Destroy")
        Toast.makeText(activity, " Fragment A on Destroy ", Toast.LENGTH_LONG).show()
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(tagForFavFragment, " on Detach")
        Toast.makeText(activity, " Fragment A on Detach ", Toast.LENGTH_LONG).show()
    }
}