package com.example.demoweatherapp.ui.cities

import androidx.lifecycle.ViewModel
import com.example.demoweatherapp.viewModel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CitiesListVMModule {
    @Binds
    @IntoMap
    @ViewModelKey(CitiesListViewModel::class)
    abstract fun bindCitiesListViewModel (viewModel : CitiesListViewModel): ViewModel

}