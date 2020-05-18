package com.example.carsomeweatherapp.utils

/** Represents types of Data to pass when api call takes place
 * @author Aveek
 * @author Nasrat Sharif Aveek
 * @version 1
 * @since 1.0.0
 */
enum class EnumDataState(val type: String) {
    SUCCESS("success"),
    ERROR("error"),
    FAILED("failed")
}