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


class MainActivityViewModel @Inject constructor(val remoteDataSource: RemoteDataSource): ViewModel() {
    /**
     * Live Data getWeatherDataClick event to trigger getting weather data by city name
     **/
    val getWeatherDataClick = MutableLiveData<CustomEventLiveData<Boolean>>()

    val temparatureInDegreeCelcius = MutableLiveData<String>()

    val weatherCondition = MutableLiveData<String>()

    /**
     * Observe weather data click event and triggers getWeatherDataClick event
     * @param none
     * @return none
     **/
    fun openWeatherData(){
        getWeatherDataClick.value = CustomEventLiveData(true)
    }


    fun getWeatherData() : MutableLiveData<PairLocal<String,Any>>{
        val dataUrl = MutableLiveData<PairLocal<String,Any>>()
        remoteDataSource.getWeatherDataByCityName("Kuala Lumpur")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                dataUrl.value = PairLocal(EnumDataState.SUCCESS.type,it)
            },{
                dataUrl.value = PairLocal(EnumDataState.ERROR.type,it)
            })
        return dataUrl
    }
}