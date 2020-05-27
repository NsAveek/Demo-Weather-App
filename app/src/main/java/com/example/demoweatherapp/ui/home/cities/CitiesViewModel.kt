package com.example.demoweatherapp.ui.home.cities

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demoweatherapp.core.events.ListenToCityAdapterItemCall
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject


class CitiesViewModel @Inject constructor(): ViewModel() {
    val citiesName = MutableLiveData<String>()
    val backgroundDrawable : ObservableField<Drawable> = ObservableField()
    fun passCityName(cityName : String){
        EventBus.getDefault()
            .post(ListenToCityAdapterItemCall(cityName))
    }
}