package com.example.meteohub.presentation.view

import android.content.Intent
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.meteohub.R
import com.example.meteohub.databinding.ActivityListBinding
import com.example.meteohub.di.ApplicationResLocator
import com.example.meteohub.domain.IRepository
import com.example.meteohub.domain.our_model.City
import com.example.meteohub.domain.our_model.WeeklyWeather
import com.example.meteohub.presentation.view.adapter.IClickListener
import com.example.meteohub.presentation.view.adapter.WeatherListAdapter
import com.example.meteohub.presentation.viewmodel.ListActivityViewModel
import com.example.meteohub.utils.Constants
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*


class ListActivity : AppCompatActivity() {
    private var binding: ActivityListBinding? = null

    private lateinit var listActivityViewModel: ListActivityViewModel

    private var savedCity: City? = null

    companion object {
        var BUNDLE_SELECTED_DAY_KEY: String? = "BUNDLE_SELECTED_DAY_KEY"
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)

        val view = binding!!.root
        setContentView(view)

        createViewModel()
        subscribeForLiveData()

        savedCity = listActivityViewModel.applicationResLocator.readFromPrefs()

        if (savedCity!!.lat == 0.0)
            startSettings()

        if (savedInstanceState == null)
            listActivityViewModel.publishWeatherLiveData(savedCity!!.lat, savedCity!!.lon)

        initRecycler()
    }

    override fun onStart() {
        binding?.buttonSettings?.setOnClickListener { startSettings() }
        super.onStart()
    }

    override fun onDestroy() {
        savedCity = null
        super.onDestroy()
    }

    private fun createViewModel() {
        val repository: IRepository = (applicationContext as ApplicationResLocator).appComponent.getRepository()

        listActivityViewModel = ViewModelProvider(this, object: ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ListActivityViewModel(repository, (applicationContext as ApplicationResLocator).getSelf()) as T
            }
        }).get(ListActivityViewModel::class.java)
    }

    private fun subscribeForLiveData() {     // потом прикрутить swipe refresh layout и в нем вызывать только listActivityViewModel.doGet() в коллбеке layout'а, и при этом observeLiveData() больше нигде не вызывать. В таком случае обновление загруженных данных должно произойти само если я все правильно понимаю
        listActivityViewModel.getWeatherLiveData().observe(this, this::showData)
        listActivityViewModel.getProgressLiveData().observe(this, this::showProgress)
        listActivityViewModel.getErrorLiveData().observe(this, this::showError)
    }

    private fun showError(error: Throwable) {
        Snackbar.make(binding?.root!!, error.toString(), BaseTransientBottomBar.LENGTH_LONG).show()
    }

    private fun showProgress(isVisible: Boolean) {
        if (isVisible) binding?.progressBar?.visibility = View.VISIBLE
        else binding?.progressBar?.visibility = View.GONE
    }

    private fun showData(weatherList: List<WeeklyWeather>) {
        setUpTableauData(weatherList[0])
        handleDayNightTableau(weatherList[0])
        initIcons(weatherList[0])

        binding?.viewToday?.setOnClickListener { startDetail(weatherList[0]) }

        binding?.recView!!.adapter = WeatherListAdapter(weatherList.subList(1, weatherList.size - 1), object: IClickListener {
            override fun openItem(position: Int, weather: WeeklyWeather) {
                startDetail(weather)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initRecycler() {
        val itemDecoration = DividerItemDecoration(binding!!.recView.context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getDrawable(R.drawable.recycler_vertical_divider)!!)
        binding!!.recView.addItemDecoration(itemDecoration)
    }

    private fun startDetail(weather: WeeklyWeather) {
        var intent = Intent(applicationContext, DetailActivity::class.java)
        intent.putExtra(BUNDLE_SELECTED_DAY_KEY, weather)
        startActivity(intent)
    }

    private fun startSettings() {
        var intent = Intent(applicationContext, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun setUpTableauData(todayData: WeeklyWeather) {
        binding?.textViewCity?.text = savedCity?.cityName
        binding?.textViewTodayDayT?.text = todayData.dayTemp
        binding?.textViewTodayNightT?.text = todayData.nightTemp
        binding?.textViewTodayWindS?.text = todayData.windSpeed
        binding?.textViewTodayDesc?.text = todayData.description
    }

    private fun handleDayNightTableau(todayData: WeeklyWeather) {
        val additionalDateFormat = SimpleDateFormat("HH:mm", Locale("ru"))
        var now = additionalDateFormat.parse(additionalDateFormat.format(Date()))

        if (now > todayData.sunriseRaw && now < todayData.sunsetRaw)
            colorizeViewBackground(false)
        else
            colorizeViewBackground(true)
    }

    private fun colorizeViewBackground(isNight: Boolean) {
        binding?.viewToday?.background?.colorFilter = when (isNight) {
            true -> BlendModeColorFilterCompat.createBlendModeColorFilterCompat(resources.getColor(R.color.main_rect_night), BlendModeCompat.SRC_ATOP)
            false -> BlendModeColorFilterCompat.createBlendModeColorFilterCompat(resources.getColor(R.color.main_rect_day), BlendModeCompat.SRC_ATOP)
        }
    }

    private fun initIcons(todayData: WeeklyWeather) {
        Picasso.get()
            .load(Constants.BASE_ICON + todayData.icon + Constants.ICON_END)
            .placeholder(R.drawable.weather_sample_ic)
            .fit()
            .into(binding?.imageViewToday)
    }
}
