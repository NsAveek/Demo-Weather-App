package com.example.carsomeweatherapp.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface BaseDAO <T>{
    @Insert
    fun insert(model : T)

    @Delete
    fun delete(model: T)

    @Update
    fun update(model : T)
}