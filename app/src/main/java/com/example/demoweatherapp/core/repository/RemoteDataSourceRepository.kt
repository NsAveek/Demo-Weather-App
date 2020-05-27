package com.example.demoweatherapp.core.repository

import com.example.demoweatherapp.core.network.AppService
import com.example.demoweatherapp.db.WeatherModel
import com.example.demoweatherapp.db.dao.WeatherDAO
import com.example.demoweatherapp.model.WeatherData
import com.example.demoweatherapp.model.forecast.ForecastData
import com.example.demoweatherapp.utils.appID
import com.example.demoweatherapp.utils.baseUnit
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class RemoteDataSourceRepository @Inject constructor(
    val appService: AppService,
    val weatherDAO: WeatherDAO
) : IWeatherRepository, ILocalStorageRepository {

    override fun getWeatherDataByCityName(cityName: String): Observable<WeatherData> {
        return appService.getWeatherByCityName(cityName, appID, baseUnit)
    }

    override fun getWeatherDataByLatLong(
        latitude: String,
        longitude: String
    ): Observable<WeatherData> {
        return appService.getWeatherByLatLong(latitude, longitude, appID, baseUnit)
    }

    override fun getWeatherForecastDataByCityName(cityName: String): Observable<ForecastData> {
        return appService.getWeatherForecastByCityName(cityName, appID, baseUnit)
    }

    override fun getWeatherForecastDataByLatLong(
        latitude: String,
        longitude: String
    ): Observable<ForecastData> {
        return appService.getWeatherForecastByLatLong(latitude, longitude, appID, baseUnit)
    }

    override fun getAllLocallyStoredWeatherData(): Single<List<WeatherModel>> {
        return weatherDAO.getAllData()
    }

    override fun insertWeatherDataIntoLocalStorage(model: WeatherModel) {
        weatherDAO.insert(model)
    }
}