package com.example.weatherapp.Network

import com.example.supersub.Utils.Constants
import com.example.weatherapp.Models.ForecastData
import com.example.weatherapp.Models.LocationKey
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherAppApi {

    @GET(Constants.LOCATION_KEY)
    fun getLocationdata(
        @Query(Constants.PARAM_APIKEY) apikey: String?,
        @Query("q") latlon: String?
    ): Call<LocationKey>

    @GET(Constants.FORECAST_DATA)
    fun getForecastdata(
        @Path("locationKey") key: Long,
        @Query(Constants.PARAM_APIKEY) apikey: String?
    ): Call<ForecastData>


}