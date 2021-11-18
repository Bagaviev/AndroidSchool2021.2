package com.example.meteohub.domain.api_entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Daily (
    @SerialName("dt")
    var dt: Long,

    @SerialName("sunrise")
    var sunrise: Int,

    @SerialName("sunset")
    var sunset: Int,

    @SerialName("moonrise")
    var moonrise: Int,

    @SerialName("moonset")
    var moonset: Int,

    @SerialName("moon_phase")
    var moonPhase: Double,

    @SerialName("temp")
    var temp: Temp?,

    @SerialName("feels_like")
    var feelsLike: FeelsLike?,

    @SerialName("pressure")
    var pressure: Int,

    @SerialName("humidity")
    var humidity: Int,

    @SerialName("dew_point")
    var dewPoint: Double,

    @SerialName("wind_speed")
    var windSpeed: Double,

    @SerialName("wind_deg")
    var windDeg: Int,

    @SerialName("wind_gust")
    var windGust: Double,

    @SerialName("weather")
    var weather: List<Weather>?,

    @SerialName("clouds")
    var clouds: Int,

    @SerialName("pop")
    var pop: Double,

    @SerialName("rain")
    var rain: Double? = null,

    @SerialName("snow")
    var snow: Double? = null,

    @SerialName("uvi")
    var uvi: Double) {
}