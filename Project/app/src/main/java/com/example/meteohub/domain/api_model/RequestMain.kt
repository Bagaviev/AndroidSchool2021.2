package com.example.meteohub.domain.api_model

import com.google.gson.annotations.SerializedName

data class RequestMain (
    @SerializedName("lat")
    var lat: Double,

    @SerializedName("lon")
    var lon: Double,

    @SerializedName("timezone")
    var timezone: String?,

    @SerializedName("timezone_offset")
    var timezoneOffset: Int,

    @SerializedName("current")
    var current: Current?,

    @SerializedName("daily")
    var daily: List<Daily>?)