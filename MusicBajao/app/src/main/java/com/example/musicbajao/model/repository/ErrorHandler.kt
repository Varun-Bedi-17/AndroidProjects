package com.example.musicbajao.model.repository

import android.util.Log
import org.xml.sax.ErrorHandler
import org.xml.sax.SAXParseException

class RetrofitErrorHandler:  ErrorHandler{
    override fun warning(p0: SAXParseException?) {
        Log.d("Warning", p0.toString())
    }

    override fun error(p0: SAXParseException?) {
        Log.d("Error", p0.toString())
    }

    override fun fatalError(p0: SAXParseException?) {
        Log.d("fatalError", p0.toString())
    }
}