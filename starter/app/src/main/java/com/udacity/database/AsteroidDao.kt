package com.udacity.database

import androidx.room.*

@Dao
interface AsteroidDao {
    @Query("SELECT * from asteroids")
    fun getAsteroids(): List<DatabaseAsteroid>

    @Query("SELECT * FROM asteroids WHERE asteroidId = :key")
    fun getAsteroidsById(key:Long): DatabaseAsteroid

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg asteroids: DatabaseAsteroid)

    @Query("SELECT * FROM picture_of_day")
    suspend fun getPictureOfTheDay(): DatabasePictureOfDay

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPictureOfTheDay(vararg pictureOfDay: DatabasePictureOfDay)
}