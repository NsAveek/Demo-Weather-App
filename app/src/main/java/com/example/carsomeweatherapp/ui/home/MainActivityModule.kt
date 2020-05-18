package com.example.carsomeweatherapp.ui.home

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides

@Module
class  MainActivityModule{
    @Provides
    fun viewModel (context : MainActivity) = ViewModelProviders.of(context).get(MainActivityViewModel::class.java)
}