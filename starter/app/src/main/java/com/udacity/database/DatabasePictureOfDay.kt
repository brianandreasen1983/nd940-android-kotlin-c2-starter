package com.udacity.database

import androidx.lifecycle.Transformations.map
import androidx.room.Entity
import androidx.room.Ignore
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

// TODO: Write an extension function that converts from a database model to a domain model
// How do we do this for a single item?
//fun DatabasePictureOfDay.asDomainModel(): PictureOfDay {
//        return map {
//                Asteroid(
//                        id = it.asteroidId,
//                        codename = it.codename,
//                        closeApproachDate = it.closeApproachDate,
//                        absoluteMagnitude = it.absoluteMagnitude,
//                        estimatedDiameter = it.estimatedDiameter,
//                        relativeVelocity = it.relativeVelocity,
//                        distanceFromEarth = it.distanceFromEarth,
//                        isPotentiallyHazardous = it.isPotentiallyHazardous
//                )
//        }
//}