package com.example.carsomeweatherapp.core.repository

import com.example.carsomeweatherapp.core.network.AppService
import com.example.carsomeweatherapp.model.WeatherData
import com.example.carsomeweatherapp.model.forecast.ForecastData
import com.example.carsomeweatherapp.utils.appID
import com.example.carsomeweatherapp.utils.baseUnit
import io.reactivex.Observable
import javax.inject.Inject

class RemoteDataSource @Inject constructor(var appService : AppService): IWeatherRepository {

    override fun getWeatherDataByCityName(cityName: String): Observable<WeatherData> {
        return appService.getWeatherByCityName(cityName, appID, baseUnit)
    }

    override fun getWeatherDataByLatLong(
        latitude: String,
        longitude: String
    ): Observable<WeatherData> {
        return appService.getWeatherByLatLong(latitude,longitude, appID, baseUnit)
    }

    override fun getWeatherForecastDataByCityName(cityName: String): Observable<ForecastData> {
        return appService.getWeatherForecastByCityName(cityName, appID, baseUnit)
    }

    override fun getWeatherForecastDataByLatLong(
        latitude: String,
        longitude: String
    ): Observable<ForecastData> {
        return appService.getWeatherForecastByLatLong(latitude,longitude, appID, baseUnit)
    }
}