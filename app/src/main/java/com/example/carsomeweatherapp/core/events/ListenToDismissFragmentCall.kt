package com.example.carsomeweatherapp.core.events

import java.io.File

class ListenToDismissFragmentCall(val dismiss: Boolean) {
    fun getMessage() = dismiss
}