package com.udacity.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDate
import java.util.*

// Type converters used for room to transition from one object to another for type compatibility and incompatibility.

class Converters {
//    @TypeConverter
//    fun fromTimeStamp(value: Long?): Date? {
//        return value?.let {
//            Date(it)
//        }
//    }
//
//    @TypeConverter
//    fun dateToTimeStamp(date: Date?): Long? {
//        return date?.time?.toLong()
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromTimeStamp(value: Long?): LocalDate? {
        return value?.let {
            LocalDate.now()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun dateToTimeStamp(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }
}