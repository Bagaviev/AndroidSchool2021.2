package com.example.meteohub.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.meteohub.domain.our_model.City

/**
 * @author Bulat Bagaviev
 * @created 20.11.2021
 */

@Database(entities = [City::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun cityDao(): CityDao
}