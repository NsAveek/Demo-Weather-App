package com.example.demoweatherapp.ui.home.cities

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.demoweatherapp.core.events.ListenToJSONCityAdapterItemCall
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class JSONCitiesViewModel @Inject constructor(): ViewModel() {
    val cityName = ObservableField<String>()
    fun onItemClick(cityName: String){
        EventBus.getDefault()
            .post(ListenToJSONCityAdapterItemCall(cityName))
    }
}