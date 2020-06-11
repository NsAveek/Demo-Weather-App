package com.example.demoweatherapp.di

import android.app.Application
import com.example.demoweatherapp.BaseApp
import com.example.demoweatherapp.di.scope.application.AppModule
import com.example.demoweatherapp.ui.cities.CitiesBottomSheetFragment
import com.example.demoweatherapp.ui.cities.CitiesBottomSheetVMModule
import com.example.demoweatherapp.ui.home.MainActivity
import com.example.demoweatherapp.di.scope.main.MainActivityModule
import com.example.demoweatherapp.di.scope.main.MainActivityVMModule
import com.example.demoweatherapp.di.scope.main.CitiesVMModule
import com.example.demoweatherapp.di.scope.main.JSONCitiesVMModule
import com.example.demoweatherapp.ui.home.cities.WeatherForecastVMModule
import com.example.demoweatherapp.viewModel.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class, // This class is responsible for all of the @singleton dependencies like retrofit, db, sharedPrefs etc
        LocalDependencyBuilder::class,
        ViewModelFactoryModule::class]
)
interface AppComponent : AndroidInjector<BaseApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance // Binds a particular instance of the object through the component of the time of construction
        fun application(application: Application): Builder // This makes the application available through all modules available
        fun build(): AppComponent
    }
    override fun inject(app: BaseApp)
}

@Module
internal abstract class LocalDependencyBuilder {
    @ContributesAndroidInjector(
        modules = [MainActivityModule::class,
            MainActivityVMModule::class,
            CitiesVMModule::class,
            WeatherForecastVMModule::class,
            JSONCitiesVMModule::class,
            MainActivityFragmentsProvider::class]
    )
    abstract fun bindMainActivity(): MainActivity
}


@Module
internal abstract class MainActivityFragmentsProvider {
    @ContributesAndroidInjector(modules = [CitiesBottomSheetVMModule::class])
    abstract fun bindCitiesBottomSheetFragment(): CitiesBottomSheetFragment
}

