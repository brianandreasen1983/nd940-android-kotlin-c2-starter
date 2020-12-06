package com.udacity.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.PictureOfDay

@Dao
interface AsteroidDao {
    @Query("select * from asteroids")
    fun getAsteroids(): LiveData<List<DatabaseAsteroid>>

    @Query("SELECT * FROM asteroids WHERE asteroidId = :key")
    fun getAsteroidsById(key:Long): DatabaseAsteroid

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DatabaseAsteroid)

    // Gives an error that the entity is not in the database.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPictureOfTheDay(vararg pictureOfDay: DatabasePictureOfDay)
}