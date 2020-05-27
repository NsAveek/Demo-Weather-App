package com.example.carsomeweatherapp.ui.home.cities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsomeweatherapp.core.events.ListenToCityAdapterItemCall
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject


class CitiesViewModel @Inject constructor(): ViewModel() {
    val citiesName = MutableLiveData<String>()
    fun passCityName(cityName : String){
        EventBus.getDefault()
            .post(ListenToCityAdapterItemCall(cityName))
    }
}