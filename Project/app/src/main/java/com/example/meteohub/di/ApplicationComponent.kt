package com.example.meteohub.di

import com.example.meteohub.data.network.NetworkModule
import com.example.meteohub.domain.IRepository
import dagger.Component

/**
 * @author Bulat Bagaviev
 * @created 19.11.2021
 */

@Component(modules = [NetworkModule::class, AppModule::class])
interface ApplicationComponent {
    fun getRepository(): IRepository
}