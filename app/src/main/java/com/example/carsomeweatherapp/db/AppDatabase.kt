package aveek.com.management.ui.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.carsomeweatherapp.db.WeatherModel
import com.example.carsomeweatherapp.db.dao.WeatherDAO


@Database(entities=[WeatherModel::class], version=1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDAO
}