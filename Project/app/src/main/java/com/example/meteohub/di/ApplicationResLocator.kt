package com.example.meteohub.di

import android.app.Application
import android.content.Context
import android.graphics.Point
import android.graphics.PointF
import android.location.LocationManager
import androidx.room.Room
import com.example.meteohub.data.db.AppDatabase
import com.example.meteohub.domain.our_model.City

/**
 * @author Bulat Bagaviev
 * @created 19.11.2021
 */

open class ApplicationResLocator: Application() {
    private var dbInstance: AppDatabase? = null
    private var locationInstance: LocationManager? = null

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.create()
    }

    override fun onCreate() {
        if (dbInstance == null) {
            dbInstance = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "city.db")
                .createFromAsset("app.db")
                .build()
        }

        if (locationInstance == null)
            locationInstance = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        super.onCreate()
    }

    fun getRoomInstance(): AppDatabase {
        return dbInstance!!
    }

    fun getLocationService(): LocationManager {
        return locationInstance!!
    }

    fun saveToPrefs(city: City) {
        getSharedPreferences("Prefs", MODE_PRIVATE).edit().apply {
            putString("cityName", city.cityName).apply()
            putFloat("lat", city.lat.toFloat()).apply()
            putFloat("lon", city.lon.toFloat()).apply()
        }
    }

    fun readFromPrefs(): City {
        val cityName = getSharedPreferences("Prefs", MODE_PRIVATE).getString("cityName", "-")
        val lat = getSharedPreferences("Prefs", MODE_PRIVATE).getFloat("lat", 0f)
        val lon = getSharedPreferences("Prefs", MODE_PRIVATE).getFloat("lon", 0f)
        return City(-1, cityName!!, "-", lat.toDouble(), lon.toDouble())
    }

    fun getSelf(): ApplicationResLocator {
        return this
    }
}