package com.example.meteohub.di

import android.app.Application
import androidx.room.Room
import com.example.meteohub.data.db.AppDatabase

/**
 * @author Bulat Bagaviev
 * @created 19.11.2021
 */

open class ApplicationResLocator: Application() {
    private var instance: AppDatabase? = null

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.create()
    }

    override fun onCreate() {
        if (instance == null) {
            instance = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "city.db")
                .createFromAsset("app.db")
                .build()
        }
        super.onCreate()
    }

    fun getRoomInstance(): AppDatabase {
        return instance!!
    }

    fun getSelf(): ApplicationResLocator {
        return this
    }
}