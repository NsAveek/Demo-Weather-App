package com.example.demoweatherapp.core.events

class ListenToJSONCityAdapterItemCall(val cityName: String) {
    fun getMessage() = cityName
}