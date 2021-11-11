package com.example.lesson19.domain.api_entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeelsLike (
    @SerialName("day")
    var day: Double,

    @SerialName("night")
    var night: Double,

    @SerialName("eve")
    var eve: Double,

    @SerialName("morn")
    var morn: Double) {
}