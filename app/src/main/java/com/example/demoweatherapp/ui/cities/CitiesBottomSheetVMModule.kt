package com.example.demoweatherapp.ui.cities

import androidx.lifecycle.ViewModel
import com.example.demoweatherapp.viewModel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CitiesBottomSheetVMModule {
    @Binds
    @IntoMap
    @ViewModelKey(CitiesBottomSheetViewModel::class)
    abstract fun bindCitiesBottomSheetViewModel (viewModel : CitiesBottomSheetViewModel): ViewModel

}