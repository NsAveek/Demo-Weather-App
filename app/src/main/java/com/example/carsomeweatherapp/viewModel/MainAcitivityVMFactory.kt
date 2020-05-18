package com.example.carsomeweatherapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carsomeweatherapp.core.repository.RemoteDataSource
import com.example.carsomeweatherapp.ui.home.MainActivityViewModel


class MainAcitivityVMFactory (private val remoteDataSource: RemoteDataSource) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(remoteDataSource) as T
    }
}