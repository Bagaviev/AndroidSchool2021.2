package com.example.meteohub.presentation.view

import android.Manifest
import android.R
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.meteohub.databinding.ActivitySettingsBinding
import com.example.meteohub.di.ApplicationResLocator
import com.example.meteohub.domain.IRepository
import com.example.meteohub.domain.our_model.City
import com.example.meteohub.presentation.viewmodel.SettingsActivityViewModel
import com.example.meteohub.utils.Constants
import com.example.meteohub.utils.Constants.Companion.GPS_PERMISSION_CODE
import com.example.meteohub.utils.Utility
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class SettingsActivity : AppCompatActivity() {
    private var binding: ActivitySettingsBinding? = null

    private lateinit var settingsActivityViewModel: SettingsActivityViewModel

    private var cityListMappingCache: HashMap<String, City>? = hashMapOf()

    private var savedCity: City? = null

    private var utils: Utility? = Utility()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)

        val view = binding!!.root
        setContentView(view)

        createViewModel()
        subscribeForLiveData()

        if (savedInstanceState == null) {
            settingsActivityViewModel.publishAllCitiesStringsLiveData()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStart() {
        handleSavedCity()

        binding?.imageButtonGps?.setOnClickListener { handleGps() }

        binding?.searchView?.setOnQueryTextFocusChangeListener { _, _ ->
            initSearchView()
            binding?.searchResultsList!!.visibility = View.VISIBLE
        }
        super.onStart()
    }

    override fun onDestroy() {
        savedCity = null
        utils = null
        cityListMappingCache?.clear()
        cityListMappingCache = null
        super.onDestroy()
    }

    private fun handleSavedCity() {
        savedCity = settingsActivityViewModel.applicationResLocator.readFromPrefs()

        if (savedCity!!.lat == 0.0) {
            val dialog = utils?.provideAlertDialog(this, Constants.NO_CITY_SELECTED)
            dialog?.show()
        }
    }

    private fun handleNoGpsModule() {
        val dialog = utils?.provideAlertDialog(this, Constants.NO_GPS_MODULE)
        dialog?.show()
    }

    private fun handleDenyPermission() {
        val dialog = utils?.provideAlertDialog(this, Constants.NO_PERMISSION)
        dialog?.show()
    }

    private fun handleDenyPermissionRoughly() {
        val dialog = utils?.provideAlertDialog(this, Constants.NO_PERMISSION_ROUGHLY)
        dialog?.show()
    }

    private fun handleAtlanticOceanLocation() {
        val dialog = utils?.provideAlertDialog(this, Constants.CITY_NOT_FOUND)
        dialog?.show()
    }

    private fun initSearchView() {
        var adapter = ArrayAdapter(this, R.layout.simple_list_item_1, cityListMappingCache!!.keys.toList())
        binding?.searchResultsList?.adapter = adapter

        binding?.searchResultsList?.setOnItemClickListener { parent, _, position, _ ->
            var cityNameSelected = parent.getItemAtPosition(position) as String

            settingsActivityViewModel.applicationResLocator.saveToPrefs(cityListMappingCache!![cityNameSelected]!!)
            showSelectedCity(cityListMappingCache!![cityNameSelected]!!)

            binding?.searchView?.clearFocus()
            binding?.searchResultsList!!.visibility = View.INVISIBLE
        }

        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String) = false

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun showSelectedCity(city: City) {
        Toast.makeText(this@SettingsActivity, "Выбран город: ${city.cityName}", Toast.LENGTH_LONG).show()
        binding?.selectedCityTv?.apply {
            text = "Город: ${city.cityName}"
            visibility = View.VISIBLE
        }
    }

    private fun createViewModel() {
        val repository: IRepository = (applicationContext as ApplicationResLocator).appComponent.getRepository()

        settingsActivityViewModel = ViewModelProvider(this, object: ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SettingsActivityViewModel(repository, (applicationContext as ApplicationResLocator).getSelf()) as T
            }
        }).get(SettingsActivityViewModel::class.java)
    }

    private fun subscribeForLiveData() {
        settingsActivityViewModel.getAllCitiesLiveData().observe(this, this::loadCityList)
        settingsActivityViewModel.getCityByCoordsLiveData().observe(this, this::showSelectedCity)
        settingsActivityViewModel.getProgressLiveData().observe(this, this::showProgress)
        settingsActivityViewModel.getErrorLiveData().observe(this, this::showError)
    }

    private fun loadCityList(cities: List<City>) {
        val keys = cities.map { it.cityName + ", " + it.countryName }
        cityListMappingCache = keys.zip(cities).toMap() as HashMap<String, City>
    }

    private fun showError(error: Throwable) {
        if (error is NoSuchElementException)
            handleAtlanticOceanLocation()
        else
            Snackbar.make(binding?.root!!, error.toString(), BaseTransientBottomBar.LENGTH_LONG).show();
    }

    private fun showProgress(isVisible: Boolean) {
        if (isVisible) binding?.progressBar2?.visibility = View.VISIBLE
        else binding?.progressBar2?.visibility = View.INVISIBLE
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun handleGps() {
        if (settingsActivityViewModel.locationModule!!.isGpsAvailableOnDevice()) {
            if (settingsActivityViewModel.locationModule!!.isLocationGranted()) {

                settingsActivityViewModel.locationModule!!.handleGpsSettings(this)
                settingsActivityViewModel.findCurrentCityAsync()

            } else
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), GPS_PERMISSION_CODE)
        } else
            handleNoGpsModule()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            GPS_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {

                        settingsActivityViewModel.locationModule!!.handleGpsSettings(this)
                        settingsActivityViewModel.findCurrentCityAsync()

                    } catch (e: SecurityException) { showError(e) }
                }
                else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION))
                    handleDenyPermission()
                else
                    handleDenyPermissionRoughly()
            }
        }
    }
}