package com.example.demoweatherapp.core.repository

import androidx.lifecycle.LiveData
import com.example.demoweatherapp.db.entity.WeatherModel
import io.reactivex.Single

interface ILocalStorageRepository {
    fun getAllLocallyStoredWeatherData() : LiveData<List<WeatherModel>>
    fun insertWeatherDataIntoLocalStorage(model : WeatherModel)
}