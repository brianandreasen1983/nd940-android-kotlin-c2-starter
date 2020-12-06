package com.udacity.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "picture_of_day")
data class DatabasePictureOfDay constructor(
        @PrimaryKey
//        var pictureOfDayId: Long,
        var title: String,
        var url: String,
        var mediaType: String
)

// Extension method to handle the converstion between Domain and Entity?
