package com.example.demoweatherapp.di.scope.application

import android.app.Application
import androidx.room.Room
import aveek.com.management.ui.db.AppDatabase
import com.example.demoweatherapp.db.dao.WeatherDAO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(applicationContext : Application) : AppDatabase {
        return Room.databaseBuilder(
            applicationContext,AppDatabase::class.java,"weather.db"
        ).build()
    }
    @Provides
    @Singleton
    fun provideWeatherDao(database: AppDatabase) : WeatherDAO{
        return database.weatherDao()
    }
}