package com.example.demoweatherapp.di.scope.main

import androidx.lifecycle.ViewModel
import com.example.demoweatherapp.ui.home.cities.CitiesViewModel
import com.example.demoweatherapp.viewModel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CitiesVMModule {
    @Binds
    @IntoMap
    @ViewModelKey(CitiesViewModel::class)
    abstract fun bindCitiesViewModel (viewModel : CitiesViewModel): ViewModel

}