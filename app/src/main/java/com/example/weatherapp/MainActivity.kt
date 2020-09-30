package com.example.weatherapp

import android.Manifest
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.supersub.Utils.getIcon
import com.example.weatherapp.Adapter.WeatherAdapter
import com.example.weatherapp.Models.DailyForecast
import com.example.weatherapp.ViewModels.ForecastListner
import com.example.weatherapp.ViewModels.WeatherViewModel
import com.example.weatherapp.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity(),ForecastListner {

    private var fusedLocationClient: FusedLocationProviderClient? = null

    private lateinit var viewModel: WeatherViewModel

    private lateinit var databinding: ActivityMainBinding

    var forecast:DailyForecast?=null

    private val listAdapter=WeatherAdapter(arrayListOf(),this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding=DataBindingUtil.setContentView(this, R.layout.activity_main)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onStart() {
        super.onStart()
        viewModel= ViewModelProviders.of(this).get(WeatherViewModel::class.java)

        if (ContextCompat.checkSelfPermission(this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION) !==
            PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            } else {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
        }else{
            fusedLocationClient!!.lastLocation
                .addOnSuccessListener(
                    this
                ) { location ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object
                        viewModel.getlocationkey(location.latitude, location.longitude)
                        // locationkey(location.latitude, location.longitude)
                    }
                }
        }

        databinding.rvForecastData.apply {
            layoutManager= LinearLayoutManager(context)
            adapter=listAdapter
        }

        observedata()
    }

    private fun observedata() {
        viewModel.loading.observe(this, Observer { isloading ->
            isloading.let {
                databinding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    databinding.tvError.visibility = View.GONE
                    databinding.ccLayout.visibility = View.GONE
                }
            }
        })

        viewModel.forecastresponse.observe(this, Observer { forecastdata ->
            forecastdata?.let {
                databinding.ccLayout.visibility = View.VISIBLE
                forecast = forecastdata.dailyForecasts[0]
                listAdapter.updateweatherlist(forecastdata.dailyForecasts)
                val max: Int = (forecast!!.temperature.maximum.value - 32) * 5 / 9
                databinding.tvTemp.text = "" + Math.round(max.toFloat()) + "\u2103"
                databinding.tvCategory.text = forecast?.day?.iconPhrase

                databinding.tvDesc.text=forecastdata.headline.text
                forecast?.day?.icon?.let { it1 ->
                    databinding.imWeatherIcon.getIcon(it1,databinding.imWeatherIcon)
                }
            }
        })

        viewModel.place.observe(this, Observer { myplace->
            myplace.let {
                databinding.tvCity.text=it
            }
        })

        viewModel.loaderror.observe(this, Observer { isError ->
            isError?.let {
                databinding.tvError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this@MainActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION) ===
                                PackageManager.PERMISSION_GRANTED)) {
                        fusedLocationClient!!.lastLocation
                            .addOnSuccessListener(
                                this
                            ) { location ->
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    // Logic to handle location object
                                    viewModel.getlocationkey(location.latitude, location.longitude)
                                    // locationkey(location.latitude, location.longitude)
                                }
                            }
                    }
                } else {
                    showSettingsDialog()
                }
                return
            }
        }

    }


    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Need Permissions")
        builder.setMessage("This app needs permission to use this feature.You can grant them in app settings.")
        builder.setPositiveButton("GOTO SETTINGS") { dialog, which ->
            dialog.cancel()
            openSettings() }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        builder.show()
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }

    override fun onClick(dailyForecast: DailyForecast) {
        val appIntent = Intent(Intent.ACTION_VIEW)
        appIntent.setPackage("com.android.chrome")
        appIntent.data = Uri.parse(dailyForecast.mobileLink)
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(dailyForecast.mobileLink)
        )
        try {
            startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            startActivity(webIntent)
        }
    }
}