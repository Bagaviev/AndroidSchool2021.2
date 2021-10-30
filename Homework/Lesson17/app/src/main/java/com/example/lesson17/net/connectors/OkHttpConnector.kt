package com.example.lesson17.net.connectors

import com.example.lesson17.net.NetConnector
import com.example.lesson17.net.NetworkRepository
import com.example.lesson17.pojo.User
import com.example.lesson17.utils.DataConverter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

/**
 * @author Bulat Bagaviev
 * @created 29.10.2021
 */

class OkHttpConnector: NetConnector {

    override fun doGet(): User? {
        var okHttpClient = OkHttpClient()

        val request: Request = Request.Builder()
            .url(NetworkRepository.URL)
            .build()

        val response: Response = okHttpClient.newCall(request).execute()
        val result: String = response.body!!.string()

        return DataConverter.convertFromJsonMoshi(result)
    }

    override fun doPost(): User? {
        var okHttpClient = OkHttpClient()
        val user = User(1, 1, "UPD", false)
        val json: String = DataConverter.convertToJsonMoshi(user)

        val body: RequestBody = json
            .toRequestBody("application/json".toMediaTypeOrNull())

        val request: Request = Request.Builder()
            .url(NetworkRepository.URL)
            .method("PUT", body)
            .addHeader("Content-type", "application/json; charset=UTF-8")
            .build()

        val response: Response = okHttpClient.newCall(request).execute()
        val result: String = response.body!!.string()

        return DataConverter.convertFromJsonMoshi(result)
    }

    override fun toString(): String {
        return javaClass.simpleName
    }
}