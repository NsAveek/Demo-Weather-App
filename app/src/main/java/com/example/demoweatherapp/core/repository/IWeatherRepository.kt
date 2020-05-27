package com.example.demoweatherapp.core.repository

import com.example.demoweatherapp.model.WeatherData
import com.example.demoweatherapp.model.forecast.ForecastData
import io.reactivex.Observable

interface IWeatherRepository {
    fun getWeatherDataByCityName(cityName : String) : Observable<WeatherData>
    fun getWeatherDataByLatLong(latitude : String, longitude : String) : Observable<WeatherData>
    fun getWeatherForecastDataByCityName(cityName : String) : Observable<ForecastData>
    fun getWeatherForecastDataByLatLong(latitude : String, longitude : String) : Observable<ForecastData>
}