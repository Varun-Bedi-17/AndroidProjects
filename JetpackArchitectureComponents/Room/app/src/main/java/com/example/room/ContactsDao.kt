package com.example.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactsDao {

    @Insert
    suspend fun insertContacts( contact : ContactsEntity )

    @Update
    suspend fun updateContacts(contact : ContactsEntity)

    @Delete
    suspend fun deleteContacts(contact : ContactsEntity)

    @Query("Select * from contact")
    fun getContacts() : LiveData<List<ContactsEntity>>
}