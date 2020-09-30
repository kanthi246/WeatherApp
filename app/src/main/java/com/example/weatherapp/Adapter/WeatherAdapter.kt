package com.example.weatherapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.supersub.Utils.getDate
import com.example.supersub.Utils.getIcon
import com.example.supersub.Utils.getday
import com.example.weatherapp.Models.DailyForecast
import com.example.weatherapp.R
import com.example.weatherapp.ViewModels.ForecastListner
import com.example.weatherapp.databinding.RowWeatherBinding
import java.util.*

class WeatherAdapter(val weatherlist: ArrayList<DailyForecast>,val onclick:ForecastListner):RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>(){

    fun updateweatherlist(newlist: List<DailyForecast>){
        weatherlist.clear()
        weatherlist.addAll(newlist)
        notifyDataSetChanged()
    }

    class WeatherViewHolder(var view: RowWeatherBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=DataBindingUtil.inflate<RowWeatherBinding>(
            inflater,
            R.layout.row_weather,
            parent,
            false
        )
        return WeatherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return weatherlist.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val forecast = weatherlist[position]
        // "\u2103" -cel
        // "\u2109" -fheat
        val max: Int = (forecast.temperature.maximum.value - 32) * 5 / 9
        val min: Int = (forecast.temperature.minimum.value - 32) * 5 / 9
        val icon: Int = forecast.day.icon

        val date: String? = getDate(forecast.epochDate.toLong())
        val day= date?.let { getday(it) }

        holder.view.weatherType.text= day+" - "+forecast.day.iconPhrase

        holder.view.weatherTemp.text="" + Math.round(max.toFloat()) + "\u00B0" + " / " + Math.round(
            min.toFloat()
        ) + "\u00B0"
        holder.view.weatherIcon.getIcon(icon,holder.view.weatherIcon)

        holder.view.webClick.setOnClickListener {
            onclick.onClick(weatherlist[position])
        }
    }
}