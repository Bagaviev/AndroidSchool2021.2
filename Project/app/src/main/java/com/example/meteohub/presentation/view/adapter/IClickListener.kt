package com.example.meteohub.presentation.view.adapter

import com.example.meteohub.domain.our_entities.WeeklyWeather

/**
 * @author Bulat Bagaviev
 * @created 14.11.2021
 */

interface IClickListener {
    fun openItem(position: Int, weather: WeeklyWeather)
}