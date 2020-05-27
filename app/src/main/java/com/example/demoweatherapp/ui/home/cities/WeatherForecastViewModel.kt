package com.example.demoweatherapp.ui.home.cities

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import javax.inject.Inject


class WeatherForecastViewModel @Inject constructor(): ViewModel() {
    val date : ObservableField<String> = ObservableField()
    val time : ObservableField<String> = ObservableField()
    val temperature : ObservableField<String> = ObservableField()
    val weatherType :ObservableField<String> = ObservableField()
    val backgroundColor : ObservableField<Int> = ObservableField()
    val iconDrawable : ObservableField<Drawable> = ObservableField()
}