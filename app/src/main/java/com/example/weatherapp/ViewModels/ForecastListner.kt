package com.example.weatherapp.ViewModels

import com.example.weatherapp.Models.DailyForecast

interface ForecastListner {
    fun onClick(dailyForecast: DailyForecast)
}