package com.example.carsomeweatherapp.utils

open class CustomEventLiveData <out T>( private val content : T) {

    var hasBeenHandled =  false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled() : T?{
        return if (hasBeenHandled) {
            null
        }else{
            hasBeenHandled = true
            content
        }
    }

    fun setHandled(handled : Boolean){
        hasBeenHandled = handled
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent() : T = content

}