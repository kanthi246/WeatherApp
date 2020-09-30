package com.example.supersub.Utils

class Constants {
    companion object{
        const val BASE_URL="http://dataservice.accuweather.com"

        const val API_KEY = "JlDayA33eMRGfyVlqkTRDbMshEf44vnM"

        const val LOCATION_KEY = "/locations/v1/cities/geoposition/search"
        const val FORECAST_DATA = "/forecasts/v1/daily/5day/{locationKey}"
        const val PARAM_APIKEY = "apikey"
    }
}