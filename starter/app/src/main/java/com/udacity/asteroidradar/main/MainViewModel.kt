package com.udacity.asteroidradar.main

import android.graphics.Picture
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.network.NasaApi
import com.udacity.network.NasaApiService
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.*
import java.lang.AssertionError
import java.lang.Exception

class MainViewModel : ViewModel() {

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> get() = _status

    private val _property = MutableLiveData<PictureOfDay>()
    val property: LiveData<PictureOfDay> get() = _property

//    private val _asteroids = MutableLiveData<ArrayList<Asteroid>>()
//    val asteroids: LiveData<ArrayList<Asteroid>> get() = _asteroids

    init{
        getNasaImageOfTheDay()
        getAsteroids()
    }

    private fun getNasaImageOfTheDay() {
        viewModelScope.launch {
            try {
                var imageOfDay = NasaApi.retrofitService.getImageOfTheDay()
                _property.value = imageOfDay
            } catch(e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }

    private fun getAsteroids() {
        viewModelScope.launch {
            try {
                val jsonResult = NasaApi.retrofitService.getAsteroids()
                var asteroids = parseAsteroidsJsonResult(jsonResult)
                print(asteroids)
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}