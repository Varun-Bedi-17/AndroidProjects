package com.example.dagger

import android.util.Log
import javax.inject.Inject


interface UserRepository{
    fun createUser(email: String, pass: String)
}

class SQLRepository @Inject constructor() : UserRepository {
    override fun createUser(email: String, pass: String) {
        Log.d(TAG, "SQL Repo")
    }
}


class FireStoreRepository(private val retryCount : Int) : UserRepository {
    override fun createUser(email: String, pass: String) {
        Log.d(TAG, "FireStore Repo $retryCount")
    }
}