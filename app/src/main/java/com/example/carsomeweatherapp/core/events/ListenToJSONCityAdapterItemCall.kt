package com.example.carsomeweatherapp.core.events

import java.io.File

class ListenToJSONCityAdapterItemCall(val cityName: String) {
    fun getMessage() = cityName
}