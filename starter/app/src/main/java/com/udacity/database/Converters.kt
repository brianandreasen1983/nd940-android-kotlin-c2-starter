package com.udacity.database

import androidx.room.TypeConverter
import java.util.*

// Type converters used for room to transition from one object to another for type compatibility and incompatibility.

class Converters {
    @TypeConverter
    fun fromTimeStamp(value: Long?): Date? {
        return value?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun dateToTimeStamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}