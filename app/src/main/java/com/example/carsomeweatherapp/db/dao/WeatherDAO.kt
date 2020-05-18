package com.example.carsomeweatherapp.db.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.carsomeweatherapp.db.WeatherModel

import io.reactivex.Single

@Dao
interface WeatherDAO  {

    @Insert
    fun insert(weatherModel : WeatherModel)

    @Delete
    fun delete(weatherModel: WeatherModel)

    @Query("SELECT * FROM `weathermodel`")

    fun getAllTransactions() : Single<List<WeatherModel>>
}