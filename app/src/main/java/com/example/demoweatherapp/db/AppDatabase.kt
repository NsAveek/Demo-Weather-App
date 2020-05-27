package aveek.com.management.ui.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.demoweatherapp.db.WeatherModel
import com.example.demoweatherapp.db.dao.WeatherDAO


@Database(entities=[WeatherModel::class], version=1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDAO
}