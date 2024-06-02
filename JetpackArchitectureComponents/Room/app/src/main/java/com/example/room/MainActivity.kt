package com.example.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var database : ContactsDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // It is not a good practice to make database like this as we make singleton objects for this database builder
//        database = Room.databaseBuilder(applicationContext,
//            ContactsDatabase::class.java,
//            "contactDB").build()

        // Creating database using singleton object.
        database = ContactsDatabase.getDatabase(this)

        GlobalScope.launch{
            database.getDAO().insertContacts(ContactsEntity("Krishan",39 , Date(), 78569)) // We can't put primary key same otherwise app will stop.
            database.getDAO().insertContacts(ContactsEntity("Varun",15 ,Date(), 78091))  // Using Secondary Constructor
        //            database.getDAO().insertContacts(ContactsEntity(8,"Virat",34))
        //            database.getDAO().insertContacts(ContactsEntity(9,"Rishabh",37))
        }
    }

    fun getData(view: View) {
        database.getDAO().getContacts().observe(this,{
            Log.i("Check database updation", it.toString())
        })
    }
}