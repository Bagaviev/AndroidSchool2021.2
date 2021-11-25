package com.example.meteohub.presentation.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
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
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

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

        val itemDecoration = DividerItemDecoration(binding!!.recView.context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getDrawable(R.drawable.own_vertical_divider)!!)
        binding!!.recView.addItemDecoration(itemDecoration)
    }

    override fun onStart() {
        binding?.buttonLoad?.setOnClickListener { startSettings() }
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
        binding?.recView!!.adapter = WeatherListAdapter(weatherList, object: IClickListener {
            override fun openItem(position: Int, weather: WeeklyWeather) {
                startDetail(weather)
            }
        })
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
}
