package com.example.lesson19.data.network

import com.example.lesson19.domain.api_entities.RequestMain

/**
 * @author Bulat Bagaviev
 * @created 10.11.2021
 */

interface INetworkApi {
    fun getWeather(): RequestMain
}