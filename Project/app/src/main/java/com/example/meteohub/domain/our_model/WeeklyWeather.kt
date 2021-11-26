package com.example.meteohub.domain.our_model

import java.io.Serializable

/**

 */

data class WeeklyWeather (
    /** @Value Поле дата */
    var dt: String?,
    var dayTemp: String?,
    var nightTemp: String?,
    var pressure: String?,
    var humidity: String?,
    var windSpeed: String?,
    var description: String?,
    var sunrise: String?,
    var sunset: String?,
    var windDeg: String?,
    var dewPoint: String?,
    var descriptionId: String?
): Serializable