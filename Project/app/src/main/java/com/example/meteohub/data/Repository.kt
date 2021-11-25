package com.example.meteohub.data

import android.location.Location
import android.location.LocationManager
import com.example.meteohub.data.db.CityDao
import com.example.meteohub.data.location.LocationModule
import com.example.meteohub.data.network.IOpenWeatherApi
import com.example.meteohub.domain.IRepository
import com.example.meteohub.domain.api_model.RequestMain
import com.example.meteohub.domain.our_model.City
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

/**
 * @author Bulat Bagaviev
 * @created 10.11.2021
 */

class Repository
@Inject constructor (var networkConnector: IOpenWeatherApi): IRepository {

    override fun loadWeatherAsync(lat: Double, lon: Double, app_id: String?): Single<RequestMain?>? {
        return networkConnector.getWeather(lat, lon, app_id)
    }

    override fun loadCitiesByCoordAsync(lat: Double, lon: Double, dbConnector: CityDao): Single<List<City>> {
        return Single.fromCallable { dbConnector.getCloseCitiesByCoords(lat, lon) }
    }

    override fun loadAllCitiesAsync(dbConnector: CityDao): Single<List<City>> {
        return Single.fromCallable { dbConnector.getAllCities() }
    }

    override fun loadLocationAsync(locationModule: LocationModule): Maybe<Location?> {
        return Maybe.fromCallable { locationModule.findLocation() }
    }
}
