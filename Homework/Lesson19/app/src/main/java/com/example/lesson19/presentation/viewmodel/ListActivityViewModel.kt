package com.example.lesson19.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lesson19.data.IRepository
import com.example.lesson19.data.converter.UsefulFieldsExtractor
import com.example.lesson19.domain.our_entities.WeeklyWeather
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author Bulat Bagaviev
 * @created 10.11.2021
 *
 * @param repository репозиторий бла бла
 */

class ListActivityViewModel (private var repository: IRepository): ViewModel() {

    /**
     * @sample mDisposable Ну поле и поле, что дальше то
     */
    private var mDisposable: Disposable? = CompositeDisposable()

    private val mWeatherLiveData = MutableLiveData<List<WeeklyWeather>>()
    private val mProgressLiveData = MutableLiveData<Boolean>()
    private val mErrorLiveData = MutableLiveData<Throwable>()

    fun doGet() {
        mDisposable = repository.loadDataAsync()

            .doOnSubscribe { mProgressLiveData.postValue(true) }
            .doAfterTerminate { mProgressLiveData.postValue(false) }
            .map { weeklyWeather -> UsefulFieldsExtractor.convert(weeklyWeather) }

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .subscribe (mWeatherLiveData::setValue, mErrorLiveData::setValue)
            // альтернативный синтаксис не работает mWeatherLiveData.value = weeklyWeather mErrorLiveData.value = throwable
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