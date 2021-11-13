package com.example.lesson19.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lesson19.data.IRepository
import com.example.lesson19.data.Repository
import com.example.lesson19.data.network.NetworkOkHttpImpl
import com.example.lesson19.databinding.ActivityListBinding
import com.example.lesson19.domain.api_entities.RequestMain
import com.example.lesson19.domain.our_entities.WeeklyWeather
import com.example.lesson19.presentation.viewmodel.ListActivityViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient

class ListActivity : AppCompatActivity() {
    private var binding: ActivityListBinding? = null
    private lateinit var listActivityViewModel: ListActivityViewModel

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
    }

    private fun showData(data: List<WeeklyWeather>) {
        binding?.textView!!.text = data.toString()
    }
}