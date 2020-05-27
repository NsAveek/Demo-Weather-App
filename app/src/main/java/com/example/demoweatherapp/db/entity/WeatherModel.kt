package com.example.demoweatherapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class WeatherModel (
        @PrimaryKey var uid : String,
        @ColumnInfo(name = "name") var cityName : String?,
        @Ignore var type : String? = null
){
    constructor():this("","","")
}

