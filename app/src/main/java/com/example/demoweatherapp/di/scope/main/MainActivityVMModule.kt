package com.example.demoweatherapp.di.scope.main

import androidx.lifecycle.ViewModel
import com.example.demoweatherapp.ui.home.MainActivityViewModel
import com.example.demoweatherapp.viewModel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainActivityVMModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainViewModel (viewModel : MainActivityViewModel): ViewModel

}