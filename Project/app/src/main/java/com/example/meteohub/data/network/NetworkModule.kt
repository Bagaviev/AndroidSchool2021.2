package com.example.meteohub.data.network

import com.example.meteohub.domain.api_entities.RequestMain
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Bulat Bagaviev
 * @created 10.11.2021
 */

class NetworkModule: IOpenWeatherApi {

    companion object {
        const val app_id = "4e58c5be8ff989deef7e876753dfb670"
        const val lat = 55.547493
        const val lon = 37.544707
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }

    private fun provideRetrofitService(): IOpenWeatherApi? {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(IOpenWeatherApi::class.java)
    }

    override fun getWeather(lat: Double, lon: Double, app_id: String?): Single<RequestMain?>? {
        return provideRetrofitService()?.getWeather(lat, lon, app_id)
    }
}