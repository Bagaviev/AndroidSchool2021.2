package com.example.lesson19.presentation.view

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
import com.example.lesson19.R
import com.example.lesson19.data.IRepository
import com.example.lesson19.data.Repository
import com.example.lesson19.data.network.NetworkOkHttpImpl
import com.example.lesson19.databinding.ActivityListBinding
import com.example.lesson19.domain.our_entities.WeeklyWeather
import com.example.lesson19.presentation.view.adapter.IClickListener
import com.example.lesson19.presentation.view.adapter.WeatherListAdapter
import com.example.lesson19.presentation.viewmodel.ListActivityViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient

class ListActivity : AppCompatActivity() {
    private var binding: ActivityListBinding? = null
    private lateinit var listActivityViewModel: ListActivityViewModel

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
        observeLiveData()

        if (savedInstanceState == null) {
            listActivityViewModel.doGet()
        }

        val itemDecoration = DividerItemDecoration(binding!!.recView.context, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(getDrawable(R.drawable.own_vertical_divider)!!)
        binding!!.recView.addItemDecoration(itemDecoration)
    }

    private fun createViewModel() {
        val repository: IRepository = Repository(NetworkOkHttpImpl(OkHttpClient(), Json))
        // сюда надо вынести инициализацию конструкторов вплоть до OkHttp зачем то

        listActivityViewModel = ViewModelProvider(this, object: ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ListActivityViewModel(repository) as T
            }
        }).get(ListActivityViewModel::class.java)
    }

    private fun observeLiveData() {     // потом прикрутить swipe refresh layout и в нем вызывать только listActivityViewModel.doGet() в коллбеке layout'а, и при этом observeLiveData() больше нигде не вызывать. В таком случае обновление загруженных данных должно произойти само если я все правильно понимаю
        listActivityViewModel.getWeatherLiveData().observe(this, this::showData)
        listActivityViewModel.getProgressLiveData().observe(this, this::showProgress);
        listActivityViewModel.getErrorLiveData().observe(this, this::showError);
    }

    private fun showError(error: Throwable) {
        Log.e("TAG", "Some exceptions from Rx: $error");
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