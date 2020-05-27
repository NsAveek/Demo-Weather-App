package com.example.demoweatherapp.network

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import com.example.demoweatherapp.BaseActivity

open class NetworkActivity : BaseActivity() {
    fun networkNotAvailable(){
        Toast.makeText(this," Network not available, Turn on the Internet please",Toast.LENGTH_LONG).show()
    }
    fun isNetworkAvailable():Boolean{
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo

        return (activeNetworkInfo != null && activeNetworkInfo.isConnected
                && activeNetworkInfo.isAvailable)
    }
}