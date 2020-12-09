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

//    val asteroids: LiveData<List<DatabaseAsteroid>> get() = _asteroids

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)



    init{
        viewModelScope.launch {
            refreshPictureOfDayFromNetwork()
            refreshAsteroidsFromNetwork()
        }
    }




//     Original code can delete this was to prove a repository concept and was originally written to get the data from the network.
    @RequiresApi(Build.VERSION_CODES.O)
    private fun refreshPictureOfDayFromNetwork() {
        viewModelScope.launch {
            try{
                asteroidRepository.refreshPictureOfTheDay()
                _imageOfDay.value = asteroidRepository.getAsteroidImageOfTheDay()
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }

    private fun refreshAsteroidsFromNetwork() {
        viewModelScope.launch {
            try {
                asteroidRepository.refreshAsteroids()
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}