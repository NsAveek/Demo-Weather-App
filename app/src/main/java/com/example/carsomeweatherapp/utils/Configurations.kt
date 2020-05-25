package com.example.carsomeweatherapp.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

const val baseUrl = "https://api.openweathermap.org/data/2.5/"
const val urlExtension = "/data/2.5/"
const val appID = "dbd3b02d8958d62185d02e944cd5f522"
const val baseUnit = "metric"
const val SHARED_PREF_NAME = "cityInfo"
const val IS_APP_RUNNING_FIRST_TIME = "is_app_running_first_time"

fun getDate(date: String): StringBuilder {

    val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date)
    val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    cal.time = date
    val year = cal[Calendar.YEAR]
    val month = cal[Calendar.MONTH]-1
    val day = cal[Calendar.DAY_OF_MONTH]
    return StringBuilder().append(year).append(" ").append(month).append(" ").append(day)
}
fun getDayOfTheWeek(date: String) : String{
    // MON
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date)
    var simpleDateFormat = SimpleDateFormat("EEE")
    return simpleDateFormat.format(date).toUpperCase()
}
fun getMonthOfTheYear(date: String) : String{
    // JUNE
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date)
    var simpleDateFormat = SimpleDateFormat("MMMM")
    return simpleDateFormat.format(date).toUpperCase()
}
fun getDateOfTheMonth(date: String) : Int{
    // 23
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date)
    val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    cal.time = date
    return cal[Calendar.DAY_OF_MONTH]
}
fun getTime(date: String) :String{
    val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val outputFormat: DateFormat =
        SimpleDateFormat("hh:mm a")
    return outputFormat.format(inputFormat.parse(date))

}
