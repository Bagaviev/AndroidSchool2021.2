package com.example.lesson19.domain.our_entities

import android.os.Parcelable
import java.io.Serializable

/**
 * @author Bulat Bagaviev
 * @created 13.11.2021
 */

data class WeeklyWeather (
    var dt: String?,
    var dayTemp: String?,
    var nightTemp: String?,
    var pressure: String?,
    var humidity: String?,
    var windSpeed: String?,
    var description: String?
): Serializable