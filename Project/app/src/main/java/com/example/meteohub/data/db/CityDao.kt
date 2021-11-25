package com.example.meteohub.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.meteohub.domain.our_model.City

/**
 * @author Bulat Bagaviev
 * @created 20.11.2021
 */

@Dao
interface CityDao {
    @Query("SELECT * FROM Cities WHERE abs(lat - :latArg) < 0.5 AND abs(lon - :lonArg) < 0.5")
    fun getCloseCitiesByCoords(latArg: Double, lonArg: Double): List<City>

    @Query("SELECT * FROM Cities")
    fun getAllCities(): List<City>

    @Insert
    fun insertAll(cities: List<City>)
}