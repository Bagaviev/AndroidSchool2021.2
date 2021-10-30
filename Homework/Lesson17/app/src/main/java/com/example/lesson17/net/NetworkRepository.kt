package com.example.lesson17.net

import com.example.lesson17.net.connectors.HttpUrlConnector
import com.example.lesson17.net.connectors.OkHttpConnector

/**
 * @author Bulat Bagaviev
 * @created 29.10.2021
 */

class NetworkRepository {
    var currentConnector: NetConnector

    init {
        currentConnector = HttpUrlConnector()
    }

    companion object {
        var URL = "https://jsonplaceholder.typicode.com/todos/1"
        var instance: NetworkRepository? = null

        fun getInstanse(): NetworkRepository {
            if (instance == null)
                instance = NetworkRepository()

            return instance as NetworkRepository
        }
    }

    fun setupConnector(type: ConnectorTypes) {
        currentConnector = when (type) {
            ConnectorTypes.HTTP_URL_CONNECTION -> HttpUrlConnector()
            else -> OkHttpConnector()
        }
    }
}