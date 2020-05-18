package com.example.carsomeweatherapp.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class ViewModelFactory<VM : ViewModel> @Inject constructor(
        private val viewModel : Lazy<VM>
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>) = viewModel.value as T
}