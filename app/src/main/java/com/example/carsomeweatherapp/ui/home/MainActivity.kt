package com.example.carsomeweatherapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import aveek.com.management.ui.db.AppDatabase
import com.example.carsomeweatherapp.BaseApp
import com.example.carsomeweatherapp.R
import com.example.carsomeweatherapp.databinding.ActivityMainBinding
import com.example.carsomeweatherapp.model.WeatherData
import com.example.carsomeweatherapp.network.NetworkActivity
import com.example.carsomeweatherapp.utils.EnumDataState
import com.example.carsomeweatherapp.viewModel.ViewModelProviderFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import javax.inject.Inject

class MainActivity : NetworkActivity(), LifecycleOwner, HasSupportFragmentInjector {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private lateinit var viewModel: MainActivityViewModel

    private lateinit var mLifecycleRegistry: LifecycleRegistry

    private lateinit var binding : ActivityMainBinding

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    // TODO : Inject Database
    private lateinit var database : AppDatabase

    private lateinit var compositeDisposable : CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        compositeDisposable = CompositeDisposable()

        viewModel = ViewModelProviders.of(this,viewModelProviderFactory).get(MainActivityViewModel::class.java)

        initDatabase()

        initBinding()

        mLifecycleRegistry = LifecycleRegistry(this).apply {
            markState(Lifecycle.State.CREATED)
        }

        handleObserver(binding)

    }
    private fun initDatabase() {
        database = AppDatabase.getAppDataBase(this)!!
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner=this // To enable Live Data object to update the XML on update
    }

    private fun handleObserver(binding: ActivityMainBinding) {
        val isNetworkAvailable = true
        binding.viewModel?.let {localViewModel ->
            with(localViewModel){
                weatherCondition.value = "Cloudy"
                getWeatherDataClick.observe(this@MainActivity, Observer { weatherLiveData ->
                    weatherLiveData.getContentIfNotHandled()?.let {
                        if (isNetworkAvailable) {
                            getWeatherData().observe(this@MainActivity, Observer {
                                it?.let { pair ->
                                    if (pair.first == EnumDataState.SUCCESS.type) {
                                        with(pair.second as WeatherData) {
                                            weatherCondition.value = this.weather[0].main
                                            temparatureInDegreeCelcius.value = String.format(this@MainActivity.getString(R.string.degree_in_celcius), this.main.temp)
                                        }
                                    } else if (pair.first == EnumDataState.ERROR.type) {
                                        with(pair.second as Throwable) {
                                            errorCallback(this)
                                        }
                                    }
                                }
                            })
                        } else {
//                            showNetWorkNotAvailableDialog()
                        }
                    }
                })
            }
        }
    }
    private fun errorCallback(error: Throwable) {
        if (error is HttpException){

        }
    }
    override fun onStart() {
        super.onStart()
        mLifecycleRegistry.markState(Lifecycle.State.STARTED)
    }

    override fun onResume() {
        super.onResume()
        mLifecycleRegistry.markState(Lifecycle.State.RESUMED)
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED)
        compositeDisposable.dispose()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }
}
