package com.udacity.network

import android.os.Build
import androidx.annotation.RequiresApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.time.LocalDate

// CONST FOR BASE URL
private const val BASE_URL = "https://api.nasa.gov/"

// API KEY CONST
private const val API_KEY = "wuXVUjrEQjwzzN9aMpHAr8zHDgIPJ6RYR89Tsckx"

private val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()

private val retrofit = Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(MoshiConverterFactory.create(moshi))
                                .build()


interface NasaApiService {
    @GET("planetary/apod?&api_key=${API_KEY}")
    suspend fun getImageOfTheDay(
        @Query("date") date: LocalDate
    ): PictureOfDay

    @GET("neo/rest/v1/feed?&api_key=${API_KEY}")
    suspend fun getAsteroids(
        @Query("start_date") startDate: LocalDate,
        @Query("end_date") endDate: LocalDate
    ): String
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object NasaApi {
    val retrofitService : NasaApiService by lazy {
        retrofit.create(NasaApiService::class.java)
    }
}