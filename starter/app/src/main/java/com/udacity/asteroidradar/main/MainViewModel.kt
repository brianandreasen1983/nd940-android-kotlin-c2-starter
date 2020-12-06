package com.udacity.asteroidradar.main

import android.app.Application
import android.graphics.Picture
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.database.AsteroidDao
import com.udacity.database.AsteroidDatabase
import com.udacity.database.AsteroidRepository
import com.udacity.network.NasaApi
import com.udacity.network.NasaApiService
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.*
import java.lang.AssertionError
import java.lang.Exception
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(private val repository: AsteroidRepository) : ViewModel() {
    private val _startDate = LocalDate.now()
    private val _endDate = _startDate.plusDays(7)

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> get() = _status

    private val _imageOfDay = MutableLiveData<PictureOfDay>()
    val imageOfDay: LiveData<PictureOfDay> get() = _imageOfDay

    private val _asteroids = MutableLiveData<ArrayList<Asteroid>>()
    val asteroids: LiveData<ArrayList<Asteroid>> get() = _asteroids

    init{
        getNasaImageOfTheDay()
        getAsteroids()
    }

    // The UI should be looking at the room database to pull the value not directly at the network call.
    // The reasoning is because then we wouldn't get any offline capabilities.
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNasaImageOfTheDay() {
        viewModelScope.launch {
            _imageOfDay.value = NasaApi.retrofitService.getImageOfTheDay(_startDate)
            repository.insertAsteroidImageOfTheDay(_imageOfDay.value!!)
        }
    }


    // The UI for the recycler view should be looking at the room database to pull the value not diretly at the network call.
    private fun getAsteroids() {
        viewModelScope.launch {
            try {
                val jsonResult = NasaApi.retrofitService.getAsteroids(_startDate, _endDate)
                _asteroids.value = parseAsteroidsJsonResult(JSONObject((jsonResult)))
                repository.insertAllAsteroids(_asteroids.value!!)
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}