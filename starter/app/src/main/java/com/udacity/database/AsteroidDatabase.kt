package com.udacity.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseAsteroid::class, DatabasePictureOfDay::class], version = 3, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao

    companion object {
        @Volatile
        private var INSTANCE: AsteroidDatabase? = null

        fun getInstance(context: Context): AsteroidDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AsteroidDatabase::class.java,
                    "asteroid_database"
                ).fallbackToDestructiveMigration()
                 .build()
                INSTANCE = instance
                instance
            }
        }
    }
}