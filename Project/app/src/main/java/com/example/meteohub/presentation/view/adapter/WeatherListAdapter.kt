package com.example.meteohub.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meteohub.R
import com.example.meteohub.domain.our_model.WeeklyWeather

/**
 * @author Bulat Bagaviev
 * @created 14.11.2021
 */

class WeatherListAdapter(private val weatherList: List<WeeklyWeather>, private val listener: IClickListener) : RecyclerView.Adapter<WeatherViewHolder>() {

    override fun onCreateViewHolder(parent:  ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = weatherList[position]

        holder.apply {
            textDt.text = weather.dt
            textDayDt.text = weather.dayTemp
            textNiDt.text = weather.nightTemp
            textDesc.text = weather.description
        }

        holder.itemView.setOnClickListener {
            listener.openItem(position, weather)
        }
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }
}

class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textDt: TextView = itemView.findViewById(R.id.textViewDt)
    val textDayDt: TextView = itemView.findViewById(R.id.textViewDayT)
    val textNiDt: TextView = itemView.findViewById(R.id.textViewNiT)
    val textDesc: TextView = itemView.findViewById(R.id.textViewDescr)
}