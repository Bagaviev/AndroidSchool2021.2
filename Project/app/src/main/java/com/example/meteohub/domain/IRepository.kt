package com.example.meteohub.domain

import android.location.Location
import com.example.meteohub.data.db.CityDao
import com.example.meteohub.data.location.LocationModule
import com.example.meteohub.domain.api_model.RequestMain
import com.example.meteohub.domain.our_model.City
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * @author Bulat Bagaviev
 * @created 11.11.2021
 */

interface IRepository {
    fun loadWeatherAsync(lat: Double, lon: Double, app_id: String?): Single<RequestMain?>?

    fun loadCitiesByCoordAsync(lat: Double, lon: Double, dbConnector: CityDao): Single<List<City>>

    fun loadAllCitiesAsync(dbConnector: CityDao): Single<List<City>>

    fun loadLocationAsync(locationModule: LocationModule): Maybe<Location?>
}