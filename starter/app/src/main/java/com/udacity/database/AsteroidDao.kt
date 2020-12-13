package com.udacity.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.main.MainViewModel
import java.time.LocalDate
import java.util.*

@Dao
interface AsteroidDao {
    @Query("SELECT * FROM asteroids WHERE close_approach_date = :date")
    fun getAsteroidsByDate(date: LocalDate): LiveData<List<DatabaseAsteroid>>

    @Query("SELECT * FROM asteroids WHERE close_approach_date BETWEEN :startDate AND :endDate")
    fun getAsteroidsByWeek(startDate: LocalDate, endDate: LocalDate): LiveData<List<DatabaseAsteroid>>

    @Query("SELECT * FROM asteroids")
    fun getAsteroids(): LiveData<List<DatabaseAsteroid>>

    @Query("SELECT * FROM picture_of_day")
    suspend fun getPictureOfTheDay(): DatabasePictureOfDay

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(asteroids: List<DatabaseAsteroid>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPictureOfTheDay(vararg pictureOfDay: DatabasePictureOfDay)
}