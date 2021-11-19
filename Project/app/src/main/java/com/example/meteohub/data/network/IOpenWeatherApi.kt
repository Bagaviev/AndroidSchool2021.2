package com.example.meteohub.data.network

import com.example.meteohub.domain.api_model.RequestMain
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IOpenWeatherApi {
    @GET("onecall?exclude=minutely,hourly,alerts&units=metric&lang=ru")
    fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") app_id: String?
    ): Single<RequestMain?>?
}