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
import com.example.meteohub.utils.Constants.Companion.GPS_PERMISSION_CODE
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class SettingsActivity : AppCompatActivity() {
    private var binding: ActivitySettingsBinding? = null

    private lateinit var settingsActivityViewModel: SettingsActivityViewModel

    private var cityListMappingCache: HashMap<String, City> = hashMapOf()

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
        binding?.imageButtonGps?.setOnClickListener { handleGps() }

        binding?.searchView?.setOnQueryTextFocusChangeListener { _, _ ->
            initSearchView()
            binding?.searchResultsList!!.visibility = View.VISIBLE
        }
        super.onStart()
    }

    override fun onDestroy() {
        cityListMappingCache.clear()
        super.onDestroy()
    }

    private fun initSearchView() {
        var adapter = ArrayAdapter(this, R.layout.simple_list_item_1, cityListMappingCache.keys.toList())
        binding?.searchResultsList?.adapter = adapter

        binding?.searchResultsList?.setOnItemClickListener { parent, _, position, _ ->
            var cityNameSelected = parent.getItemAtPosition(position) as String

            settingsActivityViewModel.applicationResLocator.saveToPrefs(cityListMappingCache[cityNameSelected]!!)
            showSelectedCity(cityListMappingCache[cityNameSelected]!!)

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
            Toast.makeText(this, "No GPS module", Toast.LENGTH_LONG).show()
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
                    Toast.makeText(this, "We need that permission", Toast.LENGTH_LONG).show()
                else
                    Toast.makeText(this, "Not working without permission", Toast.LENGTH_LONG).show()
            }
        }
    }
}