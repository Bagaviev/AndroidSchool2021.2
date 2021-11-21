package com.example.meteohub.presentation.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.meteohub.R
import com.example.meteohub.data.db.AppDatabase
import com.example.meteohub.data.network.NetworkModule
import com.example.meteohub.databinding.ActivityListBinding
import com.example.meteohub.di.MyApplication
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

    private lateinit var db: AppDatabase

    private lateinit var listActivityViewModel: ListActivityViewModel

    private var cityList = arrayListOf<City>()

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

        if (savedInstanceState == null) {
            listActivityViewModel.publishToLiveData()
        }

        val itemDecoration = DividerItemDecoration(binding!!.recView.context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getDrawable(R.drawable.own_vertical_divider)!!)
        binding!!.recView.addItemDecoration(itemDecoration)
    }

    override fun onStart() {
        db = (applicationContext as MyApplication).getRoomInstance()

        Log.e("db instance: ", "onStart: ${db.hashCode()}")

        binding?.buttonLoad?.setOnClickListener {
            loadFromDb()
        }
        super.onStart()
    }

    override fun onDestroy() {
        db.close()
        super.onDestroy()
    }

    private fun loadFromDb() {
        Thread {    // data load from app.db file happens only once per first app install, other time reference lives in app class
            val result = db.cityDao().getSampled(NetworkModule.lat, NetworkModule.lon)

            runOnUiThread {
                binding?.textViewDb?.text = result.toString()
            }
        }.start()
    }

    private fun createViewModel() {
        val repository: IRepository = (applicationContext as MyApplication).appComponent.getRepository()

        listActivityViewModel = ViewModelProvider(this, object: ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ListActivityViewModel(repository) as T
            }
        }).get(ListActivityViewModel::class.java)
    }

    private fun subscribeForLiveData() {     // потом прикрутить swipe refresh layout и в нем вызывать только listActivityViewModel.doGet() в коллбеке layout'а, и при этом observeLiveData() больше нигде не вызывать. В таком случае обновление загруженных данных должно произойти само если я все правильно понимаю
        listActivityViewModel.getWeatherLiveData().observe(this, this::showData)
        listActivityViewModel.getProgressLiveData().observe(this, this::showProgress);
        listActivityViewModel.getErrorLiveData().observe(this, this::showError);
    }

    private fun showError(error: Throwable) {
//        Log.e("TAG", "Some exceptions from Rx: $error");
        Snackbar.make(binding?.root!!, error.toString(), BaseTransientBottomBar.LENGTH_LONG).show();    // магия
    }

    private fun showProgress(isVisible: Boolean) {
        if (isVisible) binding?.progressBar?.visibility = View.VISIBLE      // магия
        else binding?.progressBar?.visibility = View.GONE
    }

    private fun showData(weatherList: List<WeeklyWeather>) {
        binding?.recView!!.adapter = WeatherListAdapter(weatherList, object: IClickListener {   // тут могло бы быть красивее, но увы как есть пока
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
}