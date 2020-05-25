package com.example.carsomeweatherapp.di.scope.application

import com.example.carsomeweatherapp.core.network.AppService
import com.example.carsomeweatherapp.utils.baseUrl
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {
    @Provides
    @Singleton
    fun provideRetrofitInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun appServiceProvider(retrofit : Retrofit) : AppService {
        return retrofit.create(AppService::class.java)
    }
}