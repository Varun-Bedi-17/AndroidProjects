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

class ContactsFragment : Fragment() {
    private val tagForContactsFragment = "Log Fragment C"
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Toast.makeText(activity, " Fragment C on Attach ", Toast.LENGTH_LONG).show()
        Log.i(tagForContactsFragment, " on Attach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(tagForContactsFragment, " on Create")
        Toast.makeText(activity, " Fragment C on Create ", Toast.LENGTH_LONG).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Toast.makeText(activity, " Fragment C on Create View ", Toast.LENGTH_LONG).show()
        Log.i(tagForContactsFragment, " on Create View ")

        val view = inflater.inflate(R.layout.contacts_fragment, container, false)
        val btn = view.findViewById<Button>(R.id.btn)
        btn.setOnClickListener{
            startActivity(Intent(activity, MainActivity2::class.java))
        }
        return view

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(tagForContactsFragment, " on View Created")
        Toast.makeText(activity, " Fragment C on View Created ", Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        Log.i(tagForContactsFragment, " on Start")
        Toast.makeText(activity, " Fragment C on Start ", Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        Log.i(tagForContactsFragment, " on Resume")
        Toast.makeText(activity, " Fragment C on Resume ", Toast.LENGTH_LONG).show()
    }

    override fun onPause() {
        super.onPause()
        Log.i(tagForContactsFragment, " on Pause")
        Toast.makeText(activity, " Fragment C on Pause ", Toast.LENGTH_LONG).show()
    }

    override fun onStop() {
        super.onStop()
        Log.i(tagForContactsFragment, " on Stop")
        Toast.makeText(activity, " Fragment C on Stop ", Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(tagForContactsFragment, " on Destroy View ")
        Toast.makeText(activity, " Fragment C on Destroy View ", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(tagForContactsFragment, " on Destroy")
        Toast.makeText(activity, " Fragment C on Destroy ", Toast.LENGTH_LONG).show()
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(tagForContactsFragment, " on Detach")
        Toast.makeText(activity, " Fragment C on Detach ", Toast.LENGTH_LONG).show()
    }
}