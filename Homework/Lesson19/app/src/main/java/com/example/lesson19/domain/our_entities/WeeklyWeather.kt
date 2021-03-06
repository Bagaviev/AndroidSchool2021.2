package com.example.lesson19.domain.our_entities

import android.os.Parcelable
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
    var description: String?
): Serializable