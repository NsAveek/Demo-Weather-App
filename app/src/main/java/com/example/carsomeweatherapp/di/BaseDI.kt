package com.example.carsomeweatherapp.di

import android.app.Application
import android.content.Context
import com.example.carsomeweatherapp.BaseApp
import com.example.carsomeweatherapp.ui.home.MainActivity
import com.example.carsomeweatherapp.ui.home.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component ( modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    LocalDependencyBuilder::class])
interface AppComponent : AndroidInjector<BaseApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance // Binds a particular instance of the object through the component of the time of construction
        fun application(application: Application): Builder // This makes the application available through all modules available
        fun build(): AppComponent
    }
    override fun inject(app : BaseApp)
}

// This class is responsible for all of the dependencies like retrofit, db, sharedPrefs etc

@Module
internal class AppModule{
    @Provides
    @Singleton
    fun provideContext (application: Application) : Context{
        return application
    }
}

@Module
internal abstract class LocalDependencyBuilder{

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity() : MainActivity
}



