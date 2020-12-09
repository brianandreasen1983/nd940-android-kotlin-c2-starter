package com.udacity.asteroidradar.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import androidx.room.Database
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.database.AsteroidRepository
import com.udacity.database.DatabaseAsteroid
import com.udacity.network.NasaAPIStatus
import com.udacity.network.NasaApi
import kotlinx.coroutines.launch
import java.lang.Exception

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(private val asteroidRepository: AsteroidRepository) : ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> get() = _status

    private val _imageOfDay = MutableLiveData<PictureOfDay>()
    val imageOfDay: LiveData<PictureOfDay> get() = _imageOfDay

    private val _asteroids = MutableLiveData<List<DatabaseAsteroid>>()
    val asteroids: LiveData<List<DatabaseAsteroid>> get() = _asteroids

    init{
        getNasaImageOfTheDay()
        getAsteroids()
    }


//     Original code can delete this was to prove a repository concept and was originally written to get the data from the network.
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNasaImageOfTheDay() {
        viewModelScope.launch {
            try{
                _imageOfDay.value = asteroidRepository.getAsteroidImageOfTheDay()
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }

    private fun getAsteroids() {
        viewModelScope.launch {
            try {
                _asteroids.value = asteroidRepository.getAsteroids().value
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}