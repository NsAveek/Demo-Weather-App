package com.example.carsomeweatherapp.core.repository

import com.example.carsomeweatherapp.model.WeatherData
import io.reactivex.Observable
import io.reactivex.Single
import org.json.JSONObject

interface IWeatherRepository {
    fun getWeatherDataByCityName(cityName : String) : Observable<WeatherData>
}