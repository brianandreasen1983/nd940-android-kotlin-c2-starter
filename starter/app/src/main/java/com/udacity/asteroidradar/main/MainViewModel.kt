package com.udacity.asteroidradar.main

import android.app.job.JobScheduler
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
import com.udacity.network.asDatabaseModel
import com.udacity.network.asDomainModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.Exception

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(private val asteroidRepository: AsteroidRepository) : ViewModel() {

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> get() = _status

    private val _imageOfDay = MutableLiveData<PictureOfDay>()
    val imageOfDay: LiveData<PictureOfDay> get() = _imageOfDay

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids = asteroidRepository.asteroids

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    // Defined for navigation
    private val _navigateToAsteroidDetails = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetails get() = _navigateToAsteroidDetails

    enum class AsteroidFilter {
        ALL,
        WEEKLY,
        TODAY,
    }

    init {
        viewModelScope.launch {
            refreshPictureOfDayFromNetwork()
            refreshAsteroidsFromNetwork(AsteroidFilter.WEEKLY)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun refreshPictureOfDayFromNetwork() {
        viewModelScope.launch {
            try {
                _status.value = NasaAPIStatus.LOADING.toString()
                asteroidRepository.refreshPictureOfTheDay()
                _imageOfDay.value = asteroidRepository.getAsteroidImageOfTheDay()
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            } finally {
                _status.value = NasaAPIStatus.DONE.toString()
            }
        }
    }

    private fun refreshAsteroidsFromNetwork(filter: AsteroidFilter) {
        viewModelScope.launch {
            try {
                _status.value = NasaAPIStatus.LOADING.toString()
                asteroidRepository.refreshAsteroids()
                _asteroids.value = asteroidRepository.getAsteroids().value as List<Asteroid>?
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            } finally {
                _status.value = NasaAPIStatus.DONE.toString()
            }
        }
    }

    fun getAsteroids() {
        viewModelScope.launch {
            _asteroids.value = asteroidRepository.getAsteroids().value as List<Asteroid>?
            print(_asteroids.value)
        }
    }

    fun getWeeklyAsteroids() {
        viewModelScope.launch {
            _asteroids.value = asteroidRepository.getWeeklyAsteroids().value as List<Asteroid>?
            print(_asteroids.value)
        }
    }

    fun getTodayAsteroids() {
        viewModelScope.launch {
            _asteroids.value = asteroidRepository.getTodayAsteroids().value as List<Asteroid>?
            print(_asteroids.value)
        }
    }



    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToAsteroidDetails.value = asteroid
    }

    fun onAsteroidClickedNavigated() {
        _navigateToAsteroidDetails.value = null
    }
}