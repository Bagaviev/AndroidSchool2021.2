package com.example.meteohub.di

import com.example.meteohub.domain.IRepository
import com.example.meteohub.data.Repository
import dagger.Binds
import dagger.Module

/**
 * @author Bulat Bagaviev
 * @created 19.11.2021
 */

@Module
interface AppModule {
    @Binds
    fun convertInterfaceToImpl(instance: Repository): IRepository
}
