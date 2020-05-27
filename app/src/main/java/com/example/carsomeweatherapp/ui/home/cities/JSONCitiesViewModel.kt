package com.example.carsomeweatherapp.ui.home.cities

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.carsomeweatherapp.core.events.ListenToJSONCityAdapterItemCall
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class JSONCitiesViewModel @Inject constructor(): ViewModel() {
    val cityName = ObservableField<String>()
    fun onItemClick(cityName: String){
        EventBus.getDefault()
            .post(ListenToJSONCityAdapterItemCall(cityName))
    }
}