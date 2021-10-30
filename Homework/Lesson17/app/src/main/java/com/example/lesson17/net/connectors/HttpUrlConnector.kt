package com.example.lesson17.net.connectors

import com.example.lesson17.net.NetConnector
import com.example.lesson17.net.NetworkRepository
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

    override fun doGet(): String {
        lateinit var connection: HttpsURLConnection

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
                return "Response code: $responseCode"
            } else {
                var builder = StringBuilder()
                var reader = BufferedReader(InputStreamReader(connection.inputStream))

                var line: String? = ""
                while (line != null) {      // криво тк в котлине нельзя присваивать в условии while (...)
                    line = reader.readLine()
                    if (line == null)
                        break
                    builder.append(line).append("\n")
                }
                return builder.toString()
            }
        } catch (e: Exception) {
            return e.toString()
        } finally {
            connection.disconnect()
        }
    }

    override fun doPost(): String {
        lateinit var connection: HttpsURLConnection
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

            var data = "{\"userId\":1,\"id\":1,\"title\":\"UPD\",\"completed\":false}"
            var writer = BufferedWriter(OutputStreamWriter(connection.outputStream))

            writer.write(data)
            writer.flush()

            connection.connect()
            var responseCode = connection.responseCode

            if (responseCode != 200) {
                return "Response code: $responseCode"
            } else {
                var builder = StringBuilder()
                var reader = BufferedReader(InputStreamReader(connection.inputStream))

                var line: String? = ""
                while (line != null) {      // криво тк в котлине нельзя присваивать в условии while (...)
                    line = reader.readLine()
                    if (line == null)
                        break
                    builder.append(line).append("\n")
                }
                return builder.toString()
            }
        } catch (e: Exception) {
            return e.toString()
        } finally {
            connection.disconnect()
        }
    }

    override fun toString(): String {
        return javaClass.simpleName
    }
}