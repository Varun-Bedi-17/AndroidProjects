package com.example.room

import androidx.room.TypeConverter
import java.util.*

class Convertors {

    @TypeConverter
    fun fromDateToLong(value : Date) : Long = value.time

    @TypeConverter
    fun fromLongToDate(value : Long) : Date = Date(value)
}