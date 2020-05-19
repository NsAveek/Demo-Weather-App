package com.example.carsomeweatherapp.di

import android.app.Application
import android.content.Context
import com.example.carsomeweatherapp.BaseApp
import com.example.carsomeweatherapp.core.network.AppService
import com.example.carsomeweatherapp.core.repository.RemoteDataSource
import com.example.carsomeweatherapp.ui.home.MainActivity
import com.example.carsomeweatherapp.ui.home.MainActivityModule
import com.example.carsomeweatherapp.ui.home.MainActivityVMModule
import com.example.carsomeweatherapp.ui.home.cities.CitiesVMModule
import com.example.carsomeweatherapp.utils.appID
import com.example.carsomeweatherapp.utils.baseUrl
import com.example.carsomeweatherapp.viewModel.ViewModelFactoryModule
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.*
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Singleton
@Component ( modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    LocalDependencyBuilder::class,ViewModelFactoryModule::class])
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

    @Provides
    @Singleton
    fun provideRetrofitInstance() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun appServiceProvider(retrofit : Retrofit) : AppService{
        return retrofit.create(AppService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource() : RemoteDataSource {
        return RemoteDataSource(appServiceProvider(retrofit = provideRetrofitInstance()))
    }
}

@Module
internal abstract class LocalDependencyBuilder{

    @ContributesAndroidInjector(modules = [MainActivityModule::class,MainActivityVMModule::class,CitiesVMModule::class])
    abstract fun bindMainActivity() : MainActivity
}



