package com.example.demoweatherapp.core.events

class ListenToCityAdapterItemCall(val cityName: String) {
    fun getMessage() = cityName
}