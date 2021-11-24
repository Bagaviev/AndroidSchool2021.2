package com.example.meteohub.di

import android.app.Application
import android.content.Context
import android.location.LocationManager
import androidx.room.Room
import com.example.meteohub.data.db.AppDatabase

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

    fun getSelf(): ApplicationResLocator {
        return this
    }
}