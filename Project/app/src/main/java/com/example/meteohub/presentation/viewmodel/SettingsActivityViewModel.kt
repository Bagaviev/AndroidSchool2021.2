package com.example.meteohub.presentation.viewmodel

import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meteohub.data.db.AppDatabase
import com.example.meteohub.data.location.LocationModule
import com.example.meteohub.di.ApplicationResLocator
import com.example.meteohub.domain.IRepository
import com.example.meteohub.domain.our_model.City
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Bulat Bagaviev
 * @created 22.11.2021
 */

class SettingsActivityViewModel
@Inject constructor(var repository: IRepository,
                    var applicationResLocator: ApplicationResLocator): ViewModel() {

    private var mDisposable: CompositeDisposable? = CompositeDisposable()
    private val mErrorLiveData = MutableLiveData<Throwable>()
    private val mProgressLiveData = MutableLiveData<Boolean>()

    private val mAllCityLiveData = MutableLiveData<List<City>>()

    private var appDb: AppDatabase? = applicationResLocator.getRoomInstance()
    var locationModule: LocationModule? = LocationModule(applicationResLocator)

    fun publishAllCitiesStringsLiveData() {     // запрос в бд по названию города, возвращаем список городов с похожим названием
        var disposable = repository.loadAllCitiesAsync(appDb?.cityDao()!!)

            .doOnSubscribe { mProgressLiveData.postValue(true) }
            .doAfterTerminate { mProgressLiveData.postValue(false) }

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .subscribe (mAllCityLiveData::setValue, mErrorLiveData::setValue)

        mDisposable?.add(disposable)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun publishCityByCurrentLocationLiveData(location: Location?) { // по локации запрос в бд, получаем скоращенный список близлежащих городов и из него находим один самый близкий
        var disposable = repository.loadCitiesByCoordAsync(location?.latitude!!, location.longitude, appDb?.cityDao()!!)

            .doOnSubscribe { mProgressLiveData.postValue(true) }
            .doAfterTerminate { mProgressLiveData.postValue(false) }

            .map { item -> locationModule?.getClosestCity(location, item)}

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .doOnError (mErrorLiveData::setValue)
            .subscribe { item -> applicationResLocator.saveToPrefs(item!!) }

        mDisposable?.add(disposable)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun findCurrentCityAsync() {    // нашли локацию (метод рычаг из активити) далее управление идет в publishCityByCurrentLocationLiveData
        var disposable = repository.loadLocationAsync(locationModule!!)

            .doOnSubscribe { mProgressLiveData.postValue(true) }
            .doAfterTerminate { mProgressLiveData.postValue(false) }

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .doOnError (mErrorLiveData::setValue)
            .subscribe { item -> publishCityByCurrentLocationLiveData(item) }

        mDisposable?.add(disposable)
    }

    override fun onCleared() {
        appDb?.close()
        appDb = null
        locationModule = null
        if (!mDisposable?.isDisposed!!) {
            mDisposable?.dispose()
            mDisposable = null
        }
        super.onCleared()
    }

    fun getAllCitiesLiveData(): LiveData<List<City>> {
        return mAllCityLiveData
    }

    fun getErrorLiveData(): LiveData<Throwable> {
        return mErrorLiveData
    }

    fun getProgressLiveData(): LiveData<Boolean> {
        return mProgressLiveData
    }
}
