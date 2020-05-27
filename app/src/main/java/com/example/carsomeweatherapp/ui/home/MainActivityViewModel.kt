package com.example.carsomeweatherapp.ui.home

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsomeweatherapp.R
import com.example.carsomeweatherapp.core.repository.RemoteDataSourceRepository
import com.example.carsomeweatherapp.db.WeatherModel
import com.example.carsomeweatherapp.db.dao.WeatherDAO
import com.example.carsomeweatherapp.utils.CustomEventLiveData
import com.example.carsomeweatherapp.utils.EnumDataState
import com.example.carsomeweatherapp.utils.PairLocal
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(
    private val remoteDataSourceRepository: RemoteDataSourceRepository,
    private val appContext: Application
) :
    ViewModel() {
    /**
     * Live Data getWeatherDataClick event to trigger getting weather data by city name
     **/
    val getWeatherDataClick = MutableLiveData<CustomEventLiveData<Boolean>>()

    val getLocationRequestClick = MutableLiveData<CustomEventLiveData<Boolean>>()

    val getShowAllCitiesClick = MutableLiveData<CustomEventLiveData<Boolean>>()

    val cityName = ObservableField<String>()

    val latitude = ObservableField<String>()

    val longitude = ObservableField<String>()

    val temparatureInDegreeCelcius = MutableLiveData<String>()

    val weatherCondition = MutableLiveData<String>()

    /**
     * Observe weather data click event and triggers getWeatherDataClick event
     * @param none
     * @return none
     **/
    fun openWeatherData() {
        getWeatherDataClick.value = CustomEventLiveData(true)
    }

    fun openLocationRequest() {
        getLocationRequestClick.value = CustomEventLiveData(true)
    }

    fun openCitiesList() {
        getShowAllCitiesClick.value = CustomEventLiveData(true)
    }


    fun getWeatherData(): MutableLiveData<PairLocal<String, Any>> {
        val data = MutableLiveData<PairLocal<String, Any>>()
        cityName.get()?.let {
            remoteDataSourceRepository.getWeatherDataByCityName(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    data.value = PairLocal(EnumDataState.SUCCESS.type, it)
                }, {
                    data.value = PairLocal(EnumDataState.ERROR.type, it)
                })
        }
        return data
    }

    fun getWeatherForecastData(): MutableLiveData<PairLocal<String, Any>> {
        val data = MutableLiveData<PairLocal<String, Any>>()
        cityName.get()?.let {
            remoteDataSourceRepository.getWeatherForecastDataByCityName(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    data.value = PairLocal(EnumDataState.SUCCESS.type, it)
                }, {
                    data.value = PairLocal(EnumDataState.ERROR.type, it)
                })
        }
        return data
    }

    fun getWeatherDataByLatLong(): MutableLiveData<PairLocal<String, Any>> {
        val data = MutableLiveData<PairLocal<String, Any>>()
        latitude!!.get()?.let {
            longitude.get()?.let { it1 ->
                remoteDataSourceRepository.getWeatherDataByLatLong(it, it1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        data.value = PairLocal(EnumDataState.SUCCESS.type, it)
                    }, {
                        data.value = PairLocal(EnumDataState.ERROR.type, it)
                    })
            }
        }
        return data
    }

    fun getWeatherForecastDataByLatLong(): MutableLiveData<PairLocal<String, Any>> {
        val data = MutableLiveData<PairLocal<String, Any>>()
        latitude.get()?.let {
            longitude.get()?.let { it1 ->
                remoteDataSourceRepository.getWeatherForecastDataByLatLong(it, it1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        data.value = PairLocal(EnumDataState.SUCCESS.type, it)
                    }, {
                        data.value = PairLocal(EnumDataState.ERROR.type, it)
                    })
            }
        }
        return data
    }

    fun insertInitialDataInsideDB() {
        with(remoteDataSourceRepository){
            insertWeatherDataIntoLocalStorage(
                WeatherModel(
                    UUID.randomUUID().toString(),
                    appContext.getString(R.string.kuala_lumpur)
                )
            )
            insertWeatherDataIntoLocalStorage(
                WeatherModel(
                    UUID.randomUUID().toString(),
                    appContext.getString(R.string.george_town)
                )
            )
            insertWeatherDataIntoLocalStorage(
                WeatherModel(
                    UUID.randomUUID().toString(),
                    appContext.getString(R.string.johor_bahru)
                )
            )
        }
    }

    fun insertDataInsideWeatherDB(model: WeatherModel){
        remoteDataSourceRepository.insertWeatherDataIntoLocalStorage(model)
    }

    fun getAllLocallyStoredWeatherData() : MutableLiveData<PairLocal<String,Any>>{
        val data = MutableLiveData<PairLocal<String, Any>>()
        remoteDataSourceRepository.getAllLocallyStoredWeatherData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                data.value = PairLocal(EnumDataState.SUCCESS.type, it)
            }, {
                data.value = PairLocal(EnumDataState.ERROR.type, it)
            })
        return data
    }
}