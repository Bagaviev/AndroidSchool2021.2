package com.example.meteohub.di

import android.app.Application
import androidx.room.Room
import com.example.meteohub.data.db.AppDatabase
import dagger.Module
import dagger.Provides

/**
 * @author Bulat Bagaviev
 * @created 19.11.2021
 */

open class MyApplication: Application() {
    private var instance: AppDatabase? = null

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.create()
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

    fun getSelf(): MyApplication {
        return this
    }
}