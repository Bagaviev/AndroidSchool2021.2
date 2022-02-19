package com.example.meteohub.data.network

import com.example.meteohub.domain.api_model.RequestMain
import com.example.meteohub.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Bulat Bagaviev
 * @created 10.11.2021
 */

@Module
class NetworkModule: IOpenWeatherApi {

    @Provides
    fun provideRetrofitService(): IOpenWeatherApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(IOpenWeatherApi::class.java)
    }

    override fun getWeather(lat: Double, lon: Double, app_id: String?): Single<RequestMain?>? {
        return provideRetrofitService().getWeather(lat, lon, app_id)
    }

    fun loadIcons() {

    }
}