package com.example.weatherapp.ViewModels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.supersub.Utils.Constants
import com.example.weatherapp.Models.ForecastData
import com.example.weatherapp.Models.LocationKey
import com.example.weatherapp.Network.WeatherAppApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel(application: Application):BaseViewModel(application) {

    // The internal MutableLiveData list that stores the most recent response
    private val _forecastresponse = MutableLiveData<ForecastData>()
    // The external immutable LiveData for the response list
    val forecastresponse: LiveData<ForecastData>
        get() = _forecastresponse

    val place=MutableLiveData<String>()
    val loaderror= MutableLiveData<Boolean>() //it'll show error if issue in retriving the data
    val loading= MutableLiveData<Boolean>() //it'll show loading so that data is not yet arrived from api

    init {
        loaderror.value = false
        loading.value = false
    }

    fun getlocationkey(latitude: Double, longitude: Double){
        loading.value=true
        val latlong = "$latitude,$longitude"
        latlong.let {
            launch {
                WeatherAppApiService.getClient.getLocationdata(Constants.API_KEY, it)
                    .enqueue(object :Callback<LocationKey>{
                        override fun onResponse(call: Call<LocationKey>,
                            response: Response<LocationKey>) {
                            if(response.raw().code()==200){
                                val locationkey=response.body()?.key
                                place.value=response.body()?.localizedName
                                getforecastdata(locationkey)

                            }else{
                                loaderror.value=false
                                loading.value=false
                                Toast.makeText(getApplication(),"AccessToken Expired",Toast.LENGTH_SHORT).show()
                            }

                        }

                        override fun onFailure(call: Call<LocationKey>, t: Throwable) {
                            loading.value=false
                            loaderror.value=true
                            Log.d("error",""+t.message)
                            Toast.makeText(getApplication(),t.message, Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }

    }

    fun getforecastdata(value: String?){
        val locationkey = value?.toLong()
        launch {
            locationkey.let {
                it?.let { it1 ->
                    WeatherAppApiService.getClient.getForecastdata(it1,Constants.API_KEY)
                        .enqueue(object :Callback<ForecastData>{
                            override fun onResponse(call: Call<ForecastData>,
                                                    response: Response<ForecastData>) {
                                if(response.raw().code()==200){
                                    _forecastresponse.value=response.body()
                                    loaderror.value=false
                                    loading.value=false
                                }else{
                                    loaderror.value=false
                                    loading.value=false
                                    Toast.makeText(getApplication(),"AccessToken Expired",Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<ForecastData>, t: Throwable) {
                                loading.value=false
                                loaderror.value=true
                                Log.d("error",""+t.message)
                                Toast.makeText(getApplication(),t.message, Toast.LENGTH_SHORT).show()
                            }
                        })
                }
            }
        }
    }
}