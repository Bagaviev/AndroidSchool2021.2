package com.example.lesson19.data.converter

import com.example.lesson19.domain.api_entities.RequestMain
import com.example.lesson19.domain.our_entities.WeeklyWeather
import java.lang.String
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Bulat Bagaviev
 * @created 13.11.2021
 */

class UsefulFieldsExtractor {

    companion object {
        private const val HPA_TO_HGMM_COEF = 1.333

        fun convert(mainRequest: RequestMain): List<WeeklyWeather> {
            val result = arrayListOf<WeeklyWeather>()
            var dailies = mainRequest.daily

            if (dailies != null) {
                val dateFormat = SimpleDateFormat("EEEE, d MMMM", Locale("ru"))

                for (day in dailies) {
                    val dt = dateFormat.format(Date(day.dt * 1000))
                    val dayTemp = day.temp?.day
                    val nightTemp = day.temp?.night
                    val pressure = day.pressure / HPA_TO_HGMM_COEF
                    val humidity = day.humidity
                    val windSpeed = day.windSpeed
                    val description = day.weather?.get(0)?.description?.lowercase()

                    result.add(WeeklyWeather(
                        dt.capitalize(),        // ранее тут была самописная хрень
                        "День: " + String.format("%d", Math.round(dayTemp!!)) + "°C",
                        "Ночь: " + String.format("%d", Math.round(nightTemp!!)) + "°C",
                        "Давление: " + String.format("%.0f", pressure) + " мм.рт",
                        "Влажность: " + String.format("%d", humidity) + "%",
                        "Ветер: " + String.format("%.0f", windSpeed) + " м/с",
                        description?.capitalize()
                    ))
                }
            }

            return result
        }
    }
}