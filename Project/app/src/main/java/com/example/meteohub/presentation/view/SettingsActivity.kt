package com.example.meteohub.presentation.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

    private var locationManager: LocationManager? = null

    private lateinit var settingsActivityViewModel: SettingsActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)

        val view = binding!!.root
        setContentView(view)

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        createViewModel()
        subscribeForLiveData()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        binding?.buttonLoadCoords?.setOnClickListener { settingsActivityViewModel.publishCitiesByCoordLiveData() }
        binding?.buttonLoadName?.setOnClickListener { settingsActivityViewModel.publishCitiesByNameLiveData() }
        binding?.buttonPermission?.setOnClickListener { handlePermission() }
        super.onStart()
    }

    override fun onDestroy() {
        locationManager = null
        super.onDestroy()
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
        settingsActivityViewModel.getCitiesCoordLiveData().observe(this, this::showCity)
        settingsActivityViewModel.getProgressLiveData().observe(this, this::showProgress)
        settingsActivityViewModel.getErrorLiveData().observe(this, this::showError)
        settingsActivityViewModel.getCitiesNameLiveData().observe(this, this::showCity)
    }

    private fun showCity(cities: List<City>) {
        binding?.textViewDb?.text = cities.toString()
    }

    private fun showError(error: Throwable) {
        Snackbar.make(binding?.root!!, error.toString(), BaseTransientBottomBar.LENGTH_LONG).show();
    }

    private fun showProgress(isVisible: Boolean) {
        if (isVisible) binding?.progressBar2?.visibility = View.VISIBLE
        else binding?.progressBar2?.visibility = View.INVISIBLE
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun handlePermission() {
        if (isLocationGranted()) {
            if (!isGpsEnabled()!!) startGpsSettings()

            binding?.textViewDb?.text = "gps request data..."
            /*locationManager!!.requestSingleUpdate(
                LocationManager.GPS_PROVIDER,
                locationListener,
                null
            )
            locationObj = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)*/

        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), GPS_PERMISSION_CODE)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            GPS_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        if (!isGpsEnabled()!!) startGpsSettings()

                        /*locationManager.requestSingleUpdate(
                        LocationManager.GPS_PROVIDER,
                        locationListener,
                        null
                    )
                    locationObj =
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)*/
//                        updLocation(locationObj)  // метод запроса GPS в репозиторий вынести, также сверить корректность в лекции Василия

                    } catch (e: SecurityException) { binding?.textViewDb?.text = e.message }
                }
                else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(this, "We need that permission", Toast.LENGTH_LONG).show()
                } else {
                    binding?.textViewDb?.text = "Not working without permission"
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isLocationGranted() =
        checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun isGpsEnabled() =
        locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER)

    private fun startGpsSettings() =
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
}