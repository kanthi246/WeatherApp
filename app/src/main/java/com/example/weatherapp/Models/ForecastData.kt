package com.example.weatherapp.Models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class ForecastData(
    @SerializedName("Headline")
    var headline: Headline,
    @SerializedName("DailyForecasts")
    var dailyForecasts: List<DailyForecast>
)

data class Headline(
    @SerializedName("EffectiveDate")
    var effectiveDate: String,
    @SerializedName("EffectiveEpochDate")
    val effectiveEpochDate: Int,
    @SerializedName("Severity")
    val severity: Int,
    @SerializedName("Text")
    val text: String,
    @SerializedName("Category")
    val category: String,
    @SerializedName("EndDate")
    val endDate: String,
    @SerializedName("EndEpochDate")
    val endEpochDate: Int,
    @SerializedName("MobileLink")
    val mobileLink: String,
    @SerializedName("Link")
    var link: String
)


data class DailyForecast(
    @SerializedName("Date")
    var date: String,
    @SerializedName("EpochDate")
    val epochDate: String,
    @SerializedName("Temperature")
    val temperature: Temperature,
    @SerializedName("Day")
    val day: Day,
    @SerializedName("Night")
    val night: Night,
    @SerializedName("Sources")
    val sources: List<String>,
    @SerializedName("MobileLink")
    val mobileLink: String,
    @SerializedName("Link")
    var link: String
)

data class Day(
    @SerializedName("Icon")
    var icon: Int,
    @SerializedName("IconPhrase")
    val iconPhrase: String,
    @SerializedName("HasPrecipitation")
    val hasPrecipitation: Boolean,
    @SerializedName("PrecipitationType")
    val precipitationType: String,
    @SerializedName("PrecipitationIntensity")
    val precipitationIntensity: String
)

data class Night(
    @SerializedName("Icon")
    var icon: Int,
    @SerializedName("IconPhrase")
    val iconPhrase: String,
    @SerializedName("HasPrecipitation")
    val hasPrecipitation: Boolean,
    @SerializedName("PrecipitationType")
    val precipitationType: String,
    @SerializedName("PrecipitationIntensity")
    val precipitationIntensity: String
)

data class Temperature(
    @SerializedName("Minimum")
    var minimum: Minimum,
    @SerializedName("Maximum")
    val maximum: Maximum
)

data class Minimum(
    @SerializedName("Value")
    var value: Int,
    @SerializedName("Unit")
    val unit: String,
    @SerializedName("UnitType")
    var unitType: Int)

data class Maximum(
    @SerializedName("Value")
    var value: Int,
    @SerializedName("Unit")
    val unit: String,
    @SerializedName("UnitType")
    var unitType: Int)