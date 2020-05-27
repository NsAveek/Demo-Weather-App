package com.example.demoweatherapp.di.scope.application

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.demoweatherapp.utils.SHARED_PREF_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferenceModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }
    @Singleton
    @Provides
    fun provideSharedPreferencesEditor(sharedPreferences: SharedPreferences): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

}