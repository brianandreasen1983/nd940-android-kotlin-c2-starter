package com.udacity.asteroidradar.main

import android.app.Application
import com.udacity.database.AsteroidDatabase
import com.udacity.database.AsteroidRepository

// This class is used to encapsulate the database and the repository instantiation for only when they are needed over when the app starts everytime.
class AsteroidApplication : Application() {
    val asteroidDatabase by lazy { AsteroidDatabase.getInstance(this) }
    val repository by lazy { AsteroidRepository(asteroidDatabase.asteroidDao) }
}