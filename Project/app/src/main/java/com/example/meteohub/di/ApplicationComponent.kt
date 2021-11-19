package com.example.meteohub.di

import com.example.meteohub.data.IRepository
import com.example.meteohub.data.Repository
import com.example.meteohub.data.network.NetworkModule
import com.example.meteohub.presentation.view.ListActivity
import dagger.Component

/**
 * @author Bulat Bagaviev
 * @created 19.11.2021
 */

@Component(modules = [NetworkModule::class])
interface ApplicationComponent {

//    fun inject(activity: ListActivity)

    fun getRepository(): Repository
}