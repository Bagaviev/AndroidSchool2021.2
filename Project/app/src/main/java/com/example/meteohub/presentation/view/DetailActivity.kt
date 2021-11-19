package com.example.meteohub.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.meteohub.databinding.ActivityDetailBinding
import com.example.meteohub.domain.our_model.WeeklyWeather

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
        binding?.textViewDayTDet?.text = dayWeather.dayTemp

        binding?.textViewNiTDet?.text = dayWeather.nightTemp
        binding?.textViewHumDet?.text = dayWeather.humidity

        binding?.textViewPresDet?.text = dayWeather.pressure
        binding?.textViewWinSpDet?.text = dayWeather.windSpeed

        binding?.textViewDescDet?.text = dayWeather.description
    }
}