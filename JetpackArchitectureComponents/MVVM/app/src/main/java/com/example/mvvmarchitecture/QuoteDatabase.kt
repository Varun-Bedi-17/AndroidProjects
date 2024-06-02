package com.example.mvvmarchitecture

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [QuotesEntity2::class], version = 1)
abstract class QuoteDatabase : RoomDatabase(){

    abstract fun quoteDaoDB() : QuotesDao2

    companion object{
        @Volatile
        private var INSTANCE : QuoteDatabase? = null

        fun getDatabase(context : Context) : QuoteDatabase{
            if (INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context,
                        QuoteDatabase::class.java,
                        "quoteDaB")
                        .createFromAsset("quotes.db")
                        .build()
                    Log.i("KHN", "Running first")
                }
            }
            return INSTANCE!!
        }
    }
}