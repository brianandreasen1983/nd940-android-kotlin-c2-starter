package com.udacity.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay

@Dao
interface AsteroidDao {
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * from asteroids")
    suspend fun getAsteroids(): List<DatabaseAsteroid>

    @Query("SELECT * FROM asteroids WHERE asteroidId = :key")
    fun getAsteroidsById(key:Long): DatabaseAsteroid

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg asteroids: DatabaseAsteroid)

    @Query("SELECT * FROM picture_of_day")
    suspend fun getPictureOfTheDay(): DatabasePictureOfDay

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPictureOfTheDay(vararg pictureOfDay: DatabasePictureOfDay)
}