package com.example.meteohub.domain.api_entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Temp (
    @SerialName("day")
    var day: Double,

    @SerialName("min")
    var min: Double,

    @SerialName("max")
    var max: Double,

    @SerialName("night")
    var night: Double,

    @SerialName("eve")
    var eve: Double,

    @SerialName("morn")
    var morn: Double) {
}