package com.example.demoweatherapp

import android.app.Activity

import androidx.multidex.MultiDexApplication
import com.example.demoweatherapp.di.AppInjector


import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

//class BaseApp : DaggerApplication(){
//    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
//        return DaggerAppComponent.builder().application(this).build()
//    }
//}
class BaseApp : MultiDexApplication(), HasAndroidInjector{

    @Inject
    lateinit var dispatchingActivityInjector : DispatchingAndroidInjector<Any>
    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingActivityInjector
}