package com.example.demoweatherapp.di.scope.main

import androidx.lifecycle.ViewModel
import com.example.demoweatherapp.ui.home.cities.JSONCitiesViewModel
import com.example.demoweatherapp.viewModel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class JSONCitiesVMModule {
    @Binds
    @IntoMap
    @ViewModelKey(JSONCitiesViewModel::class)
    abstract fun bindJSONCitiesViewModel (viewModel : JSONCitiesViewModel): ViewModel

}