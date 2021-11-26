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
                val mainDateFormat = SimpleDateFormat("EEEE, d MMMM", Locale("ru"))
                val additionalDateFormat = SimpleDateFormat("HH:mm", Locale("ru"))

                for (day in dailies) {
                    val dt = mainDateFormat.format(Date(day.dt * 1000))
                    val dayTemp = day.temp?.day
                    val nightTemp = day.temp?.night
                    val pressure = day.pressure / HPA_TO_HGMM_COEF
                    val humidity = day.humidity
                    val windSpeed = day.windSpeed
                    val description = day.weather?.get(0)?.description?.lowercase()

                    val sunrise = additionalDateFormat.format(Date(day.sunrise.toLong() * 1000))
                    val sunset = additionalDateFormat.format(Date(day.sunset.toLong() * 1000))
                    val windDegree = day.windDeg
                    val dewPoint = day.dewPoint
                    val descriptionId = day.weather?.get(0)?.id

                    result.add(
                        WeeklyWeather(
                            dt.capitalize(),
                            "День: " + String.format("%d", Math.round(dayTemp!!)) + "°C",
                            "Ночь: " + String.format("%d", Math.round(nightTemp!!)) + "°C",
                            "Давление: " + String.format("%.0f", pressure) + " мм.рт",
                            "Влажность: " + String.format("%d", humidity) + "%",
                            "Ветер: " + String.format("%.0f", windSpeed) + " м/с",
                            description?.capitalize(),
                            "Рассвет: $sunrise",
                            "Закат: $sunset",
                            "Направление ветра: " + String.format("%d", windDegree) + "°",
                            "Точка росы: " + String.format("%d", Math.round(dewPoint!!)) + "°C",
                            String.format("%d", descriptionId)
                        )
                    )
                }
            }
            return result
        }
    }
}