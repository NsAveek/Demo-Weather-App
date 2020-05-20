package com.example.carsomeweatherapp.ui.home.cities

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carsomeweatherapp.core.events.ListenToCityAdapterItemCall
import com.example.carsomeweatherapp.core.repository.RemoteDataSource
import com.example.carsomeweatherapp.model.WeatherData
import com.example.carsomeweatherapp.utils.CustomEventLiveData
import com.example.carsomeweatherapp.utils.EnumDataState
import com.example.carsomeweatherapp.utils.PairLocal
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject


class WeatherForecastViewModel @Inject constructor(): ViewModel() {
    val date : ObservableField<String> = ObservableField()
    val time : ObservableField<String> = ObservableField()
    val temperature : ObservableField<String> = ObservableField()
    val weatherType :ObservableField<String> = ObservableField()
    val backgroundColor : ObservableField<Int> = ObservableField()
    val iconDrawable : ObservableField<Drawable> = ObservableField()
}