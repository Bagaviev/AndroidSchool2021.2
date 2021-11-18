package com.example.meteohub.data

import com.example.meteohub.domain.api_entities.RequestMain
import io.reactivex.Single

/**
 * @author Bulat Bagaviev
 * @created 11.11.2021
 */

interface IRepository {
    fun loadWeatherAsync(lat: Double, lon: Double, app_id: String?): Single<RequestMain?>?
}