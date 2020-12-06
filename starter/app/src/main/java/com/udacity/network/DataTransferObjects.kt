package com.udacity.network

import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.PictureOfDay

/**
 * DataTransferObjects go in this file. These are responsible for parsing responses from the server
 * or formatting objects to send to the server. You should convert these to domain objects before
 * using them.
 */

@JsonClass(generateAdapter = true)
data class NetworkPictureOfDayContainer(val pictureOfDay: PictureOfDay)

@JsonClass(generateAdapter = true)
data class NetworkPictureOfDay (
    var title: String,
    var url: String,
    var mediaType: String
)

//Convert network results to database objects
fun NetworkPictureOfDayContainer.asDomainModel(): PictureOfDay{
    return PictureOfDay(pictureOfDay.mediaType, pictureOfDay.title, pictureOfDay.url)
}

// Need to do the same style of changes to the asteroids for mapping reasons.