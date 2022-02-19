package com.example.meteohub.utils

/**
 * @author Bulat Bagaviev
 * @created 23.11.2021
 */

class Constants {
    companion object {
        const val APP_ID = "4e58c5be8ff989deef7e876753dfb670"
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val GPS_PERMISSION_CODE = 1;
        const val ICON_END = "@4x.png"
        var BASE_ICON = "https://openweathermap.org/img/wn/"

        const val NO_CITY_SELECTED = "Для просмотра прогноза, нужно выбрать населенный пункт, можете воспользоваться геолокацией или ручным вводом"
        const val CITY_NOT_FOUND = "Похоже, что рядом не нашлось крупного населенного пункта, попробуйте ручной выбор из списка"
        const val NO_NETWORK_CONNECTION = "Отсутствует подключение к интернету, без него погоду не загрузить :("
        const val NO_PERMISSION = "Для автоматического поиска города нужно разрешение на GPS, либо же можно воспользоваться ручным поиском"
        const val NO_PERMISSION_ROUGHLY = "Вы отказались давать разрешение на GPS, все данные на телефоне будут стерты, а к вам уже выехали"
        const val NO_GPS_MODULE = "На вашем девайсе отсутствует GPS-модуль, можете воспользоваться ручным поиском"
    }
}