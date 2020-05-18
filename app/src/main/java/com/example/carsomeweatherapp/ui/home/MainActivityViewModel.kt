package com.example.carsomeweatherapp.ui.home
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MainActivityViewModel : ViewModel() {
    val creditData = MutableLiveData<Boolean>()
    val transactionHistory = MutableLiveData<Boolean>()
    val category = MutableLiveData<Boolean>()
    val expense = MutableLiveData<Boolean>()

    val balanceText = ObservableField<String>()

    fun clickCreditData(){
        creditData.value = true
    }
    fun clickTransactionHistory(){
        transactionHistory.value = true
    }
    fun clickCategory(){
        category.value = true
    }
    fun clickExpense(){
        expense.value = true
    }

}