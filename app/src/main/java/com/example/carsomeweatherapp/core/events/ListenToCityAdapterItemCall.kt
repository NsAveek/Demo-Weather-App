package com.example.carsomeweatherapp.core.events

import java.io.File

class ListenToCityAdapterItemCall(val cityName: String) {
    fun getMessage() = cityName
}