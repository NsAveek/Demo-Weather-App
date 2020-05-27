package com.example.carsomeweatherapp.core.repository

import androidx.lifecycle.MutableLiveData
import com.example.carsomeweatherapp.db.WeatherModel
import com.example.carsomeweatherapp.model.WeatherData
import com.example.carsomeweatherapp.model.forecast.ForecastData
import com.example.carsomeweatherapp.utils.PairLocal
import io.reactivex.Observable
import io.reactivex.Single
import org.json.JSONObject

interface ILocalStorageRepository {
    fun getAllLocallyStoredWeatherData() : Single<List<WeatherModel>>
    fun insertWeatherDataIntoLocalStorage(model : WeatherModel)
}