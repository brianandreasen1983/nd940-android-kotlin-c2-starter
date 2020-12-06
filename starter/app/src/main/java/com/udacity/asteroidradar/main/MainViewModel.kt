package com.udacity.asteroidradar.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.database.AsteroidRepository
import kotlinx.coroutines.launch
import java.lang.Exception

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(private val repository: AsteroidRepository) : ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> get() = _status

    private val _imageOfDay = MutableLiveData<PictureOfDay>()
    val imageOfDay: LiveData<PictureOfDay> get() = _imageOfDay

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>> get() = _asteroids

    init{
        getNasaImageOfTheDay()
        getAsteroids()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNasaImageOfTheDay() {
        viewModelScope.launch {
            try{
                _imageOfDay.value = repository.getAsteroidImageOfTheDay()
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }


    private fun getAsteroids() {
        viewModelScope.launch {
            try {
                _asteroids.value = repository.getAsteroids()
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}