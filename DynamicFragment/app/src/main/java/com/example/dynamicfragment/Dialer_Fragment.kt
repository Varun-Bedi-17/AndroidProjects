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

class DialerFragment : Fragment() {
    private val tagForDialerFragment = "Log Fragment B"
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Toast.makeText(activity, " Fragment B on Attach ", Toast.LENGTH_LONG).show()
        Log.i(tagForDialerFragment, " on Attach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(tagForDialerFragment, " on Create")
        Toast.makeText(activity, " Fragment B on Create ", Toast.LENGTH_LONG).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Toast.makeText(activity, " Fragment B on Create View ", Toast.LENGTH_LONG).show()
        Log.i(tagForDialerFragment, " on Create View ")

        val view = inflater.inflate(R.layout.dialer_fragment, container, false)
        val btn = view.findViewById<Button>(R.id.btn)
        btn.setOnClickListener{
            startActivity(Intent(activity, MainActivity2::class.java))
        }
        return view

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(tagForDialerFragment, " on View Created")
        Toast.makeText(activity, " Fragment B on View Created ", Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        Log.i(tagForDialerFragment, " on Start")
        Toast.makeText(activity, " Fragment B on Start ", Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        Log.i(tagForDialerFragment, " on Resume")
        Toast.makeText(activity, " Fragment B on Resume ", Toast.LENGTH_LONG).show()
    }

    override fun onPause() {
        super.onPause()
        Log.i(tagForDialerFragment, " on Pause")
        Toast.makeText(activity, " Fragment B on Pause ", Toast.LENGTH_LONG).show()
    }

    override fun onStop() {
        super.onStop()
        Log.i(tagForDialerFragment, " on Stop")
        Toast.makeText(activity, " Fragment B on Stop ", Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(tagForDialerFragment, " on Destroy View ")
        Toast.makeText(activity, " Fragment B on Destroy View ", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(tagForDialerFragment, " on Destroy")
        Toast.makeText(activity, " Fragment B on Destroy ", Toast.LENGTH_LONG).show()
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(tagForDialerFragment, " on Detach")
        Toast.makeText(activity, " Fragment B on Detach ", Toast.LENGTH_LONG).show()
    }


}