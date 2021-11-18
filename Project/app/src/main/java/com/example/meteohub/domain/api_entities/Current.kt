package com.example.meteohub.domain.api_entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// https://www.json2kotlin.com/

@Serializable
data class Current(
    @SerialName("dt")
    var dt: Int,

    @SerialName("sunrise")
    var sunrise: Int,

    @SerialName("sunset")
    var sunset: Int,

    @SerialName("temp")
    var temp: Double,

    @SerialName("feels_like")
    var feelsLike: Double,

    @SerialName("pressure")
    var pressure: Int,

    @SerialName("humidity")
    var humidity: Int,

    @SerialName("dew_point")
    var dewPoint: Double,

    @SerialName("uvi")
    var uvi: Double,

    @SerialName("clouds")
    var clouds: Int,

    @SerialName("visibility")
    var visibility: Int,

    @SerialName("wind_speed")
    var windSpeed: Double,

    @SerialName("wind_deg")
    var windDeg: Int,

    @SerialName("weather")
    var weather: List<Weather>?) {
}