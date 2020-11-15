package com.udacity.network

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

// CONST FOR BASE URL
private const val BASE_URL = "https://api.nasa.gov/"

// API KEY CONST
private const val API_KEY = ""

private val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()

private val retrofit = Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(MoshiConverterFactory.create(moshi))
                                .build()


// TODO: The filters for the dates will need to be dynamic and not statically set.
interface NasaApiService {
    @GET("planetary/apod?date=2020-11-08&api_key=${API_KEY}")
    suspend fun getImageOfTheDay(): PictureOfDay

    @GET("neo/rest/v1/feed?start_date=2020-11-09&end_date=2020-11-16&api_key=${API_KEY}")
    suspend fun getAsteroids(): JSONObject
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object NasaApi {
    val retrofitService : NasaApiService by lazy {
        retrofit.create(NasaApiService::class.java)
    }
}