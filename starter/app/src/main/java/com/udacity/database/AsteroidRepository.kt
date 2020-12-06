package com.udacity.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.WorkerThread
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.network.NasaApi
import org.json.JSONObject
import java.time.LocalDate

// Repository for the asteroids
class AsteroidRepository(private val asteroidDao: AsteroidDao) {
    @RequiresApi(Build.VERSION_CODES.O)
    private val _startDate = LocalDate.now()
    @RequiresApi(Build.VERSION_CODES.O)
    private val _endDate = _startDate.plusDays(7)

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun refreshAsteroids() {
        val jsonResult = NasaApi.retrofitService.getAsteroids(_startDate, _endDate)
        val asteroids = parseAsteroidsJsonResult(JSONObject((jsonResult)))

        // This could be better optimized need to figure out why we have to manually map our domain model with our datamodel this is expensive for maintenance.
        for (asteroid in asteroids) {
            val databaseAsteroid = DatabaseAsteroid(asteroid.id,
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
    }

    suspend fun refreshPictureOfTheDay() {
            val pictureOfDay =  NasaApi.retrofitService.getImageOfTheDay(_startDate)
            val databasePictureOfDay = DatabasePictureOfDay(pictureOfDay.title, pictureOfDay.url, pictureOfDay.mediaType)
            asteroidDao.insertPictureOfTheDay(databasePictureOfDay)
    }

    // Get the asteroid Image of the day from the database?
    @WorkerThread
    suspend fun getAsteroidImageOfTheDay(): PictureOfDay {
        val databasePictureOfDay = asteroidDao.getPictureOfTheDay()
        val pic = PictureOfDay(databasePictureOfDay.mediaType, databasePictureOfDay.title, databasePictureOfDay.url)
        return pic
    }

    @WorkerThread
    suspend fun getAsteroids(): List<Asteroid> {
        val dbAsteroids =  asteroidDao.getAsteroids()
        val asteroids = mutableListOf<Asteroid>()
        for (dbAsteroid in dbAsteroids) {
            val asteroid = Asteroid(dbAsteroid.asteroidId,
                                    dbAsteroid.codename,
                                    dbAsteroid.closeApproachDate,
                                    dbAsteroid.absoluteMagnitude,
                                    dbAsteroid.estimatedDiameter,
                                    dbAsteroid.relativeVelocity,
                                    dbAsteroid.distanceFromEarth,
                                    dbAsteroid.isPotentiallyHazardous)

            asteroids.add(asteroid)
        }

        return asteroids
    }

    // Insert the asteroid image of the day to the database.
    @WorkerThread
    suspend fun insertAsteroidImageOfTheDay(pictureOfDay: PictureOfDay) {
        val pic = pictureOfDay
        val databasePic = DatabasePictureOfDay(pic.title, pic.url, pic.mediaType)
        // Map the properties from the pictureOfDay to the DatabasePictureOfDay object.

        // Throws an error that we cannot access the database on the main thread since it may potentially lock the UI for a long time.
        asteroidDao.insertPictureOfTheDay(databasePic)
    }

    @WorkerThread
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
    }
}
