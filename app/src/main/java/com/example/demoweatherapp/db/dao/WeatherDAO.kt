package com.example.demoweatherapp.db.dao
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.demoweatherapp.db.entity.WeatherModel

import io.reactivex.Single

@Dao
interface WeatherDAO : BaseDAO<WeatherModel> {

    @Query("SELECT * FROM `weathermodel`")
    fun getAllData() : LiveData<List<WeatherModel>>
}