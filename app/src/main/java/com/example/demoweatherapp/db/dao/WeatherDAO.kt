package com.example.demoweatherapp.db.dao
import androidx.room.Dao
import androidx.room.Query
import com.example.demoweatherapp.db.WeatherModel

import io.reactivex.Single

@Dao
interface WeatherDAO : BaseDAO<WeatherModel> {

    @Query("SELECT * FROM `weathermodel`")
    fun getAllData() : Single<List<WeatherModel>>
}