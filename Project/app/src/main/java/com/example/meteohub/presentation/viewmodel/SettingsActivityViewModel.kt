package com.example.meteohub.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meteohub.data.db.AppDatabase
import com.example.meteohub.data.network.NetworkModule
import com.example.meteohub.di.ApplicationResLocator
import com.example.meteohub.domain.IRepository
import com.example.meteohub.domain.our_model.City
import com.example.meteohub.utils.Constants.Companion.CITY_NAME
import com.example.meteohub.utils.Constants.Companion.LAT
import com.example.meteohub.utils.Constants.Companion.LON
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Bulat Bagaviev
 * @created 22.11.2021
 */

class SettingsActivityViewModel
@Inject constructor(var repository: IRepository,
                    var applicationResLocator: ApplicationResLocator): ViewModel() {

    private var mDisposable: Disposable? = CompositeDisposable()
    private val mErrorLiveData = MutableLiveData<Throwable>()
    private val mProgressLiveData = MutableLiveData<Boolean>()

    private val mCityCoordsLiveData = MutableLiveData<List<City>>()
    private val mCityNameLiveData = MutableLiveData<List<City>>()
    private var appDb: AppDatabase? = applicationResLocator.getRoomInstance()

    fun publishCitiesByCoordLiveData() {
        mDisposable = repository.loadCitiesByCoordAsync(LAT, LON, appDb?.cityDao()!!)

            .doOnSubscribe { mProgressLiveData.postValue(true) }
            .doAfterTerminate { mProgressLiveData.postValue(false) }

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .subscribe (mCityCoordsLiveData::setValue)
    }

    fun publishCitiesByNameLiveData() {
        mDisposable = repository.loadCitiesByNameAsync(CITY_NAME.lowercase(), appDb?.cityDao()!!)

            .doOnSubscribe { mProgressLiveData.postValue(true) }
            .doAfterTerminate { mProgressLiveData.postValue(false) }

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .subscribe (mCityNameLiveData::setValue)
    }

    override fun onCleared() {
        appDb?.close()
        appDb = null
        super.onCleared()
        if (mDisposable != null && !mDisposable!!.isDisposed) {
            mDisposable!!.dispose()
            mDisposable = null
        }
    }

    fun getCitiesCoordLiveData(): LiveData<List<City>> {
        return mCityCoordsLiveData
    }

    fun getCitiesNameLiveData(): LiveData<List<City>> {
        return mCityNameLiveData
    }

    fun getErrorLiveData(): LiveData<Throwable> {
        return mErrorLiveData
    }

    fun getProgressLiveData(): LiveData<Boolean> {
        return mProgressLiveData
    }
}