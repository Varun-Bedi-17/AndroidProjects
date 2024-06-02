package com.example.room

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [ContactsEntity::class], version = 2)
@TypeConverters(Convertors::class)
abstract class ContactsDatabase() : RoomDatabase() {

    abstract fun getDAO() : ContactsDao

    // Singleton object for database builder
    companion object {
        @Volatile                 // This will reflect instance variable in all threads.
        private var INSTANCE : ContactsDatabase? = null

        // For updated version using migration
        val migration1_2 = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE contact ADD COLUMN number INTEGER NOT NULL DEFAULT(1)")
            }

        }

        fun getDatabase(context : Context) : ContactsDatabase{
            if (INSTANCE == null){
                // In kotlin, we can have multiple threads for different operations. So, it is
                // possible that different threads can create different instances of a single database.
                // Therefore, we use blocking such that different threads can't create different instances
                // of a single database.
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,      // application context bcz room is only one for whole application
                        ContactsDatabase::class.java,
                        "contactDB").addMigrations(migration1_2).build()
                }
            }
            return INSTANCE!!
        }


    }


}