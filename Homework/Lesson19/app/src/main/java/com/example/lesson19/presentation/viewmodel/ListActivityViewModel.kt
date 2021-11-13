package com.example.lesson19.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lesson19.data.IRepository
import com.example.lesson19.data.converter.UsefulFieldsExtractor
import com.example.lesson19.domain.api_entities.RequestMain
import com.example.lesson19.domain.our_entities.WeeklyWeather
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author Bulat Bagaviev
 * @created 10.11.2021
 */

class ListActivityViewModel (private var repository: IRepository): ViewModel() {

    private var mDisposable: Disposable? = CompositeDisposable()
    private val mWeatherLiveData = MutableLiveData<List<WeeklyWeather>>()

    fun doGet() {
        mDisposable = repository.loadDataAsync()
            .map { weeklyWeather -> UsefulFieldsExtractor.convert(weeklyWeather) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { weeklyWeather -> mWeatherLiveData.setValue(weeklyWeather) }
    }

    override fun onCleared() {
        super.onCleared()
        if (mDisposable != null && !mDisposable!!.isDisposed) {
            mDisposable!!.dispose()
            mDisposable = null
        }
    }

    fun getWeatherLiveData(): LiveData<List<WeeklyWeather>> {
        return mWeatherLiveData
    }
}