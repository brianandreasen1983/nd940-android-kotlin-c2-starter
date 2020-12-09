package com.udacity.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.google.gson.JsonObject
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.network.NasaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.LocalDate

// Repository for the asteroids

// There are mapping problems to solve in the database here in which we will need to solve using our helper functions
class AsteroidRepository(private val asteroidDatabase: AsteroidDatabase) {

    @RequiresApi(Build.VERSION_CODES.O)
    private val _startDate = LocalDate.now()
    @RequiresApi(Build.VERSION_CODES.O)
    private val _endDate = _startDate.plusDays(7)

    @RequiresApi(Build.VERSION_CODES.O)
    val asteroids: LiveData<List<Asteroid>> = Transformations.map(asteroidDatabase.asteroidDao.getAsteroids()) {
         it.asDomainModel()
    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val jsonResult = NasaApi.retrofitService.getAsteroids()
            // Parse the json result and then store them all in the database.
            val asteroids = parseAsteroidsJsonResult(JSONObject(jsonResult))
            val listDbAsteroids = mutableListOf<DatabaseAsteroid>()
            print(asteroids)

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

                listDbAsteroids.add(databaseAsteroid)
            }

            asteroidDatabase.asteroidDao.insertAll(listDbAsteroids.toList())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun refreshPictureOfTheDay() {
        withContext(Dispatchers.IO) {
            print(LocalDate.now())
            val pictureOfDay = NasaApi.retrofitService.getImageOfTheDay()
            val dbPictureOfDay = DatabasePictureOfDay(pictureOfDay.url, pictureOfDay.title, pictureOfDay.mediaType)
            asteroidDatabase.asteroidDao.insertPictureOfTheDay(dbPictureOfDay)
        }
    }

    @WorkerThread
    suspend fun getAsteroidImageOfTheDay(): PictureOfDay {
        val databasePictureOfDay = asteroidDatabase.asteroidDao.getPictureOfTheDay()
        return PictureOfDay(databasePictureOfDay.mediaType, databasePictureOfDay.title, databasePictureOfDay.url)
    }

    @WorkerThread
    suspend fun getAsteroids(): LiveData<List<DatabaseAsteroid>> {
        return asteroidDatabase.asteroidDao.getAsteroids()
    }
}
