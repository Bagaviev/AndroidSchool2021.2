package com.example.lesson19.data.network

import com.example.lesson19.domain.api_entities.RequestMain
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

/**
 * @author Bulat Bagaviev
 * @created 10.11.2021
 */

class NetworkOkHttpImpl (
    private val okHttpClient: OkHttpClient,
    private var jsonSerializer: Json
) : INetworkApi {

    companion object {
        private val app_id = "4e58c5be8ff989deef7e876753dfb670"
        private val lat = 55.547493
        private val lon = 37.544707
        private val base = "https://api.openweathermap.org/data/2.5/"
        var URL = base + "onecall?exclude=minutely,hourly,alerts&units=metric&lang=ru" + "&lat=$lat"  + "&lon=$lon" + "&appid=$app_id"
    }

    override fun getWeather(): RequestMain {
        val format = Json(jsonSerializer) { ignoreUnknownKeys = true }      // костыль, но после переписывания с java без него никак

        val request: Request = Request.Builder()
            .url(URL)
            .build()

        val response: Response = okHttpClient.newCall(request).execute()
        val result: String = response.body!!.string()

        response.close()
        return format.decodeFromString(result)
    }
}