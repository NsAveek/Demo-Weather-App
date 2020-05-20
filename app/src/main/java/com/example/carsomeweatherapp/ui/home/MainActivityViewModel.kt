package com.example.carsomeweatherapp.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsomeweatherapp.core.repository.RemoteDataSource
import com.example.carsomeweatherapp.model.WeatherData
import com.example.carsomeweatherapp.utils.CustomEventLiveData
import com.example.carsomeweatherapp.utils.EnumDataState
import com.example.carsomeweatherapp.utils.PairLocal
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(val remoteDataSource: RemoteDataSource) :
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

    fun openCitiesList(){
        getShowAllCitiesClick.value = CustomEventLiveData(true)
    }


    fun getWeatherData(): MutableLiveData<PairLocal<String, Any>> {
        val dataUrl = MutableLiveData<PairLocal<String, Any>>()
        cityName.get()?.let {
            remoteDataSource.getWeatherDataByCityName(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataUrl.value = PairLocal(EnumDataState.SUCCESS.type, it)
                }, {
                    dataUrl.value = PairLocal(EnumDataState.ERROR.type, it)
                })
        }
        return dataUrl
    }

    fun getWeatherForecastData(): MutableLiveData<PairLocal<String, Any>> {
        val dataUrl = MutableLiveData<PairLocal<String, Any>>()
        cityName.get()?.let {
            remoteDataSource.getWeatherForecastDataByCityName(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataUrl.value = PairLocal(EnumDataState.SUCCESS.type, it)
                }, {
                    dataUrl.value = PairLocal(EnumDataState.ERROR.type, it)
                })
        }
        return dataUrl
    }

    fun getWeatherDataByLatLong(): MutableLiveData<PairLocal<String, Any>> {
        val dataUrl = MutableLiveData<PairLocal<String, Any>>()
        latitude!!.get()?.let {
            longitude.get()?.let { it1 ->
                remoteDataSource.getWeatherDataByLatLong(it, it1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        dataUrl.value = PairLocal(EnumDataState.SUCCESS.type, it)
                    }, {
                        dataUrl.value = PairLocal(EnumDataState.ERROR.type, it)
                    })
            }
        }
        return dataUrl
    }

    fun getWeatherForecastDataByLatLong(): MutableLiveData<PairLocal<String, Any>> {
        val dataUrl = MutableLiveData<PairLocal<String, Any>>()
        latitude.get()?.let {
            longitude.get()?.let { it1 ->
                remoteDataSource.getWeatherForecastDataByLatLong(it, it1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        dataUrl.value = PairLocal(EnumDataState.SUCCESS.type, it)
                    }, {
                        dataUrl.value = PairLocal(EnumDataState.ERROR.type, it)
                    })
            }
        }


        return dataUrl
    }

}