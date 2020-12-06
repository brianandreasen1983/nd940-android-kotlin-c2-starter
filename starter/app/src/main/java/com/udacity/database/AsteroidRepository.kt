package com.udacity.database

import android.graphics.Picture
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.network.NasaApi
import java.lang.Exception

// Repository for the asteroids
class AsteroidRepository(private val asteroidDao: AsteroidDao) {
//    suspend fun refreshAsteroids() {
//        // TODO: Not yet implemented.
//    }

    // Get the asteroid Image of the day from the database?
//    suspend fun getAsteroidImageOfTheDay() {
//        // TODO: Not yet implemented.
//        print("Shit is here.")
//    }

    // Insert the asteroid image of the day to the database.
    suspend fun insertAsteroidImageOfTheDay(pictureOfDay: PictureOfDay) {
        val pic = pictureOfDay
        val databasePic = DatabasePictureOfDay(pic.title, pic.url, pic.mediaType)
        // Map the properties from the pictureOfDay to the DatabasePictureOfDay object.

        // Throws an error that we cannot access the database on the main thread since it may potentially lock the UI for a long time.
        //asteroidDao.insertPictureOfTheDay(databasePic)
    }

    suspend fun insertAllAsteroids(asteroids: ArrayList<Asteroid>) {
        // Loop through the asteroids and map their properties to the database asteroid and insert them.

        // Inserting each record one by one is slow....Is there a faster way?
        for ( asteroid in asteroids) {
            val databaseAsteroid = DatabaseAsteroid(
                    asteroid.id,
                    asteroid.codename,
                    asteroid.closeApproachDate,
                    asteroid.absoluteMagnitude,
                    asteroid.estimatedDiameter,
                    asteroid.relativeVelocity,
                    asteroid.distanceFromEarth,
                    asteroid.isPotentiallyHazardous
            )

            asteroidDao.insertAll(databaseAsteroid)
        }

        print(asteroids)
    }
}
