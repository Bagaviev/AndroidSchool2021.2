package com.example.meteohub.domain

import com.example.meteohub.data.db.CityDao
import com.example.meteohub.domain.api_model.RequestMain
import com.example.meteohub.domain.our_model.City
import io.reactivex.Single

/**
 * @author Bulat Bagaviev
 * @created 11.11.2021
 */

interface IRepository {
    fun loadWeatherAsync(lat: Double, lon: Double, app_id: String?): Single<RequestMain?>?

    fun loadCitiesByCoordAsync(lat: Double, lon: Double, dbConnector: CityDao): Single<List<City>>

    fun loadCitiesByNameAsync(cityName: String, dbConnector: CityDao): Single<List<City>>
}