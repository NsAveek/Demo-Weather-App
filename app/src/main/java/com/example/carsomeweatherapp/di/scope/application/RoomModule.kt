package com.example.carsomeweatherapp.di.scope.application

import android.app.Application
import androidx.room.Room
import aveek.com.management.ui.db.AppDatabase
import com.example.carsomeweatherapp.db.dao.WeatherDAO
import dagger.Module
import dagger.Provides
import java.lang.annotation.RetentionPolicy
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
class RoomModule {


    /*
    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "weatherDB").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
    * */


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