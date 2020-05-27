package com.example.demoweatherapp.ui.cities

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.demoweatherapp.core.events.ListenToDismissFragmentCall
import com.example.demoweatherapp.core.events.ListenToJSONCityAdapterItemCall
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class CitiesListViewModel @Inject constructor(): ViewModel() {
    val cityName = ObservableField<String>()

    fun onItemClick(cityName: String){
        EventBus.getDefault()
            .post(ListenToDismissFragmentCall(true))
        EventBus.getDefault()
            .post(ListenToJSONCityAdapterItemCall(cityName))
    }
}