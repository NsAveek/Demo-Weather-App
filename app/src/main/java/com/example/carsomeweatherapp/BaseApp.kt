package com.example.carsomeweatherapp

import android.app.Activity

import androidx.multidex.MultiDexApplication
import com.example.carsomeweatherapp.di.AppInjector


import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

//class BaseApp : DaggerApplication(){
//    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
//        return DaggerAppComponent.builder().application(this).build()
//    }
//}
class BaseApp : MultiDexApplication() , HasActivityInjector{

    @Inject
    lateinit var dispatchingActivityInjector : DispatchingAndroidInjector<Activity>
    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }
    override fun activityInjector(): AndroidInjector<Activity> {
        return  dispatchingActivityInjector
    }
}