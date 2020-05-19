package com.example.carsomeweatherapp.ui.home.cities

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsomeweatherapp.core.events.ListenToCityAdapterItemCall
import com.example.carsomeweatherapp.core.events.ListenToJSONCityAdapterItemCall
import com.example.carsomeweatherapp.core.repository.RemoteDataSource
import com.example.carsomeweatherapp.model.WeatherData
import com.example.carsomeweatherapp.utils.CustomEventLiveData
import com.example.carsomeweatherapp.utils.EnumDataState
import com.example.carsomeweatherapp.utils.PairLocal
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class JSONCitiesViewModel @Inject constructor(): ViewModel() {
    val cityName = ObservableField<String>()
    fun onItemClick(cityName: String){
        EventBus.getDefault()
            .post(ListenToJSONCityAdapterItemCall(cityName))
    }
}