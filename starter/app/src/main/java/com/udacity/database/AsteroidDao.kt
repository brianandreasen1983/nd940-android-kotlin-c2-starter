package com.udacity.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.main.MainViewModel
import java.time.LocalDate
import java.util.*

@Dao
interface AsteroidDao {
//    // We want to be able to get the asteroids from today if that is selected for a date range
//    @Query("SELECT * FROM asteroids WHERE close_approach_date = :date")
//    fun getAsteroidsByDate(date: Long): DatabaseAsteroid
//
//    // We also want to be able to get the asteroids from this week if that date range is selected.
//    @Query("SELECT * FROM asteroids WHERE close_approach_date BETWEEN :startDate AND :endDate")
//    fun getAsteroidsByWeek(startDate: Long, endDate: Long): LiveData<List<DatabaseAsteroid>>

    // We should also get all of the saved asteroids if that is selected.
    @Query("SELECT * FROM asteroids")
    fun getAsteroids(): LiveData<List<DatabaseAsteroid>>

    @Query("SELECT * FROM picture_of_day")
    suspend fun getPictureOfTheDay(): DatabasePictureOfDay

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(asteroids: List<DatabaseAsteroid>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPictureOfTheDay(vararg pictureOfDay: DatabasePictureOfDay)
}