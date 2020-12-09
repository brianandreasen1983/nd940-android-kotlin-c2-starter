package com.udacity.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDao {
    @Query("SELECT * from asteroids")
    fun getAsteroids(): LiveData<List<DatabaseAsteroid>>

    @Query("SELECT * FROM asteroids WHERE asteroidId = :key")
    fun getAsteroidsById(key:Long): DatabaseAsteroid

    @Query("SELECT * FROM picture_of_day")
    suspend fun getPictureOfTheDay(): DatabasePictureOfDay

    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    @JvmSuppressWildcards
    suspend fun insertAll(asteroids: List<DatabaseAsteroid>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPictureOfTheDay(vararg pictureOfDay: DatabasePictureOfDay)
}