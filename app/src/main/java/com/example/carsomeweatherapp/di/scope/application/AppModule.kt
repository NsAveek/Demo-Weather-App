package com.example.carsomeweatherapp.di.scope.application

import android.app.Application
import android.content.Context
import com.example.carsomeweatherapp.core.network.AppService
import com.example.carsomeweatherapp.core.repository.RemoteDataSourceRepository
import com.example.carsomeweatherapp.db.dao.WeatherDAO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [RetrofitModule::class,SharedPreferenceModule::class,RoomModule::class])
internal class AppModule{

    @Provides
    @Singleton
    fun provideContext (application: Application) : Context {
        return application
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(appService: AppService,weatherDAO: WeatherDAO) : RemoteDataSourceRepository {
        return RemoteDataSourceRepository(appService,weatherDAO)
    }
}