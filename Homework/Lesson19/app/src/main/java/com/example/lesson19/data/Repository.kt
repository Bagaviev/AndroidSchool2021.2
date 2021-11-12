package com.example.lesson19.data

import com.example.lesson19.data.network.INetworkApi
import com.example.lesson19.data.network.NetworkOkHttpImpl
import com.example.lesson19.domain.api_entities.RequestMain
import io.reactivex.Single
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient

/**
 * @author Bulat Bagaviev
 * @created 10.11.2021
 */

class Repository(var networkConnector: NetworkOkHttpImpl): IRepository {
    private var connector: INetworkApi = networkConnector

    override fun loadDataAsync(): Single<RequestMain> {
        return Single.fromCallable { connector.getWeather() }
    }
}