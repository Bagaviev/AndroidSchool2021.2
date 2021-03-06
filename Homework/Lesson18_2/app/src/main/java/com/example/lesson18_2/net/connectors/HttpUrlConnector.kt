package com.example.lesson18_2.net.connectors

import android.util.Log
import com.example.lesson18_2.net.NetConnector
import com.example.lesson18_2.net.NetworkRepository
import com.example.lesson18_2.pojo.User
import com.example.lesson18_2.utils.DataConverter
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * @author Bulat Bagaviev
 * @created 29.10.2021
 */

class HttpUrlConnector: NetConnector {

    override fun doGet(): User? {
        lateinit var connection: HttpsURLConnection
        var builder = StringBuilder()

        try {
            var url = URL(NetworkRepository.URL)
            connection = url.openConnection() as HttpsURLConnection

            connection.apply {
                connectTimeout = 3000
                readTimeout = 3000
            }

            connection.connect()
            var responseCode = connection.responseCode

            if (responseCode != 200) {
                Log.e("TAG", "Response code: $responseCode")
            } else {
                var reader = BufferedReader(InputStreamReader(connection.inputStream))
                var line: String? = ""

                while (line != null) {      // криво тк в котлине нельзя присваивать в условии while (...)
                    line = reader.readLine()

                    if (line == null)
                        break
                    builder.append(line).append("\n")
                }

                reader.close()
            }
        } catch (e: Exception) {
            connection.disconnect()
            Log.e("TAG", "Exception: $e.toString()")
        }

        connection.disconnect()
        return DataConverter.convertFromJson(builder.toString())
    }

    override fun doPost(): User? {
        lateinit var connection: HttpsURLConnection
        var builder = StringBuilder()

        try {
            var url = URL(NetworkRepository.URL)

            connection = url.openConnection() as HttpsURLConnection
            connection.apply {

                connectTimeout = 3000
                readTimeout = 3000
                addRequestProperty("Content-type", "application/json")

                requestMethod = "PUT"
                doOutput = true
            }

            var user = User(userId = 1, id = 1, title = "UPD", completed = false)
            var writer = BufferedWriter(OutputStreamWriter(connection.outputStream))

            writer.write(DataConverter.convertToJson(user).toString())
            writer.flush()

            connection.connect()
            var responseCode = connection.responseCode

            if (responseCode != 200) {
                Log.e("TAG", "Response code: $responseCode")
            } else {
                var reader = BufferedReader(InputStreamReader(connection.inputStream))
                var line: String? = ""

                while (line != null) {      // криво тк в котлине нельзя присваивать в условии while (...), костыль №1
                    line = reader.readLine()

                    if (line == null)
                        break
                    builder.append(line).append("\n")
                }

                reader.close()
                writer.close()
            }
        } catch (e: Exception) {
            connection.disconnect()
            Log.e("TAG", "Exception: $e.toString()")
        }

        connection.disconnect()
        return DataConverter.convertFromJson(builder.toString())
    }

    override fun toString(): String {
        return javaClass.simpleName
    }
}