package com.example.demoweatherapp.core.repository

import com.example.demoweatherapp.db.WeatherModel
import io.reactivex.Single

interface ILocalStorageRepository {
    fun getAllLocallyStoredWeatherData() : Single<List<WeatherModel>>
    fun insertWeatherDataIntoLocalStorage(model : WeatherModel)
}