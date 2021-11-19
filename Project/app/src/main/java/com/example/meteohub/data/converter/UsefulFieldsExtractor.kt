package com.example.meteohub.data.converter

import com.example.meteohub.domain.api_model.RequestMain
import com.example.meteohub.domain.our_model.WeeklyWeather
import java.lang.String
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Bulat Bagaviev
 * @created 13.11.2021
 */

class UsefulFieldsExtractor {

    companion object {
        /**  */
        private const val HPA_TO_HGMM_COEF = 1.333

        /**
         * Конвертер и форматтер типов данных json
         * Метод преобразующий объект запроса из сети RequestMain к самодельному списку объектов WeeklyWeather.
         *
         *
         * @param mainRequest объект запроса из OkHttpClient, составной json, содержит полный набор атрибутов предметной области погоды.
         * @return Список вспомогательных объектов, с сокращенным под наши нужды набором атрибутов, приведенными типами, а также с заданным форматированием для вывода..
         */

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

                    result.add(
                        WeeklyWeather(
                        dt.capitalize(),        // ранее тут была самописная хрень
                        "День: " + String.format("%d", Math.round(dayTemp!!)) + "°C",
                        "Ночь: " + String.format("%d", Math.round(nightTemp!!)) + "°C",
                        "Давление: " + String.format("%.0f", pressure) + " мм.рт",
                        "Влажность: " + String.format("%d", humidity) + "%",
                        "Ветер: " + String.format("%.0f", windSpeed) + " м/с",
                        description?.capitalize()
                    )
                    )
                }
            }

            return result
        }
    }
}