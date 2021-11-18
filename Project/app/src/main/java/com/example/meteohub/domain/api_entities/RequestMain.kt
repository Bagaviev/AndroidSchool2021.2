package com.example.meteohub.domain.api_entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestMain (
    @SerialName("lat")
    var lat: Double,

    @SerialName("lon")
    var lon: Double,

    @SerialName("timezone")
    var timezone: String?,

    @SerialName("timezone_offset")
    var timezoneOffset: Int,

    @SerialName("current")
    var current: Current?,

    @SerialName("daily")
    var daily: List<Daily>?) {
}