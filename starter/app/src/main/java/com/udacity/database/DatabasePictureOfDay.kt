package com.udacity.database

import androidx.lifecycle.Transformations.map
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay

@Entity(tableName = "picture_of_day")
data class DatabasePictureOfDay constructor(
        @PrimaryKey
//        var pictureOfDayId: Long,
        var title: String,
        var url: String,
        var mediaType: String
)