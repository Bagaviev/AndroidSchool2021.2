package com.example.meteohub.domain.our_model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Bulat Bagaviev
 * @created 20.11.2021
 */

@Entity(tableName = "Cities")
data class City (
    @PrimaryKey
    var id: Int,
    var cityName: String,
    var countryName: String,
    var lat: Double,
    var lon: Double
)