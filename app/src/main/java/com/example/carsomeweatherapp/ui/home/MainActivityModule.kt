package com.example.carsomeweatherapp.ui.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import com.example.carsomeweatherapp.core.network.AppService
import com.example.carsomeweatherapp.core.repository.RemoteDataSource
import com.example.carsomeweatherapp.model.Main
import com.example.carsomeweatherapp.viewModel.MainAcitivityVMFactory

import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class  MainActivityModule(){

//    @Provides
//    fun viewModel (remoteDataSource: RemoteDataSource) : MainActivityViewModel = ViewModelProviders.of(context,
//        MainAcitivityVMFactory(remoteDataSource)).get(MainActivityViewModel::class.java)

}