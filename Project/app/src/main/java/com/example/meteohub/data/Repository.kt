package com.example.meteohub.data

import com.example.meteohub.data.network.IOpenWeatherApi
import com.example.meteohub.data.network.NetworkModule
import com.example.meteohub.domain.api_model.RequestMain
import io.reactivex.Single
import javax.inject.Inject

/**
 * @author Bulat Bagaviev
 * @created 10.11.2021
 */

class Repository @Inject constructor (var networkConnector: IOpenWeatherApi) {

    fun loadWeatherAsync(lat: Double, lon: Double, app_id: String?): Single<RequestMain?>? {
        return networkConnector.getWeather(lat, lon, app_id)
    }
}
