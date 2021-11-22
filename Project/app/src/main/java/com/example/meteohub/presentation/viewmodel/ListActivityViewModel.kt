package com.example.meteohub.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meteohub.domain.IRepository
import com.example.meteohub.data.converter.UsefulFieldsExtractor
import com.example.meteohub.data.db.AppDatabase
import com.example.meteohub.data.network.NetworkModule
import com.example.meteohub.di.ApplicationResLocator
import com.example.meteohub.domain.our_model.City
import com.example.meteohub.domain.our_model.WeeklyWeather
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Bulat Bagaviev
 * @created 10.11.2021
 *
 * @param repository репозиторий бла бла
 */

class ListActivityViewModel
@Inject constructor (var repository: IRepository): ViewModel() {

    private var mDisposable: Disposable? = CompositeDisposable()
    private val mErrorLiveData = MutableLiveData<Throwable>()
    private val mProgressLiveData = MutableLiveData<Boolean>()

    private val mWeatherLiveData = MutableLiveData<List<WeeklyWeather>>()

    fun publishToLiveData() {
        mDisposable = repository.loadWeatherAsync(NetworkModule.lat, NetworkModule.lon, NetworkModule.app_id)!!

            .doOnSubscribe { mProgressLiveData.postValue(true) }
            .doAfterTerminate { mProgressLiveData.postValue(false) }

            .map { weeklyWeather -> UsefulFieldsExtractor.convert(weeklyWeather) }

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .subscribe (mWeatherLiveData::setValue, mErrorLiveData::setValue)
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

    fun getErrorLiveData(): LiveData<Throwable> {
        return mErrorLiveData
    }

    fun getProgressLiveData(): LiveData<Boolean> {
        return mProgressLiveData
    }
}