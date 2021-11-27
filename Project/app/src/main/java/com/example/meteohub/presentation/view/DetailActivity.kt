package com.example.meteohub.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.meteohub.R
import com.example.meteohub.databinding.ActivityDetailBinding
import com.example.meteohub.domain.our_model.WeeklyWeather
import com.example.meteohub.utils.Constants
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private var binding: ActivityDetailBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        val view = binding!!.root
        setContentView(view)

        val dayWeather = intent.getSerializableExtra(ListActivity.BUNDLE_SELECTED_DAY_KEY) as WeeklyWeather
        bindData(dayWeather)
    }

    private fun bindData(dayWeather: WeeklyWeather) {
        binding?.textViewDtDet?.text = dayWeather.dt

        binding?.textViewDTempDet?.text = dayWeather.dayTemp
        binding?.textViewNTempDet?.text = dayWeather.nightTemp
        binding?.textViewHumDet?.text = dayWeather.humidity

        binding?.textViewPressDet?.text = dayWeather.pressure
        binding?.textViewWindSDet?.text = dayWeather.windSpeed
        binding?.textViewWDegreeDet?.text = dayWeather.windDeg

        binding?.textViewSriseDet?.text = dayWeather.sunrise
        binding?.textViewSsetDet?.text = dayWeather.sunset
        binding?.textViewDpointDet?.text = dayWeather.dewPoint

        initIcons(dayWeather)
    }

    private fun initIcons(dayWeather: WeeklyWeather) {
        Picasso.get()
            .load(Constants.BASE_ICON + dayWeather.icon + Constants.ICON_END)
            .placeholder(R.drawable.weather_sample_ic)
            .fit()
            .into(binding?.imageViewDetail)
    }
}