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
                        dt.capitalize(),
                        String.format("%.0f", dayTemp),
                        String.format("%.0f", nightTemp),
                        String.format("%.0f", pressure),
                        String.format("%d", humidity),
                        String.format("%.0f", windSpeed),
                        description?.capitalize()
                    ))
                }
            }

            return result
        }
    }
}