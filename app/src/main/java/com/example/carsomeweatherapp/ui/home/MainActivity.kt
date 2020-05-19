package com.example.carsomeweatherapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import aveek.com.management.ui.db.AppDatabase
import com.example.carsomeweatherapp.BaseApp
import com.example.carsomeweatherapp.R
import com.example.carsomeweatherapp.databinding.ActivityMainBinding
import com.example.carsomeweatherapp.db.WeatherModel
import com.example.carsomeweatherapp.model.WeatherData
import com.example.carsomeweatherapp.network.NetworkActivity
import com.example.carsomeweatherapp.ui.home.cities.adapter.CitiesListAdapter
import com.example.carsomeweatherapp.utils.EnumDataState
import com.example.carsomeweatherapp.viewModel.ViewModelProviderFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.util.*
import javax.inject.Inject

class MainActivity : NetworkActivity(), LifecycleOwner, HasSupportFragmentInjector {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private lateinit var viewModel: MainActivityViewModel

    private lateinit var mLifecycleRegistry: LifecycleRegistry

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    // TODO : Inject Database
    private lateinit var database: AppDatabase

    private lateinit var compositeDisposable: CompositeDisposable

    private lateinit var citiesListAdapter: CitiesListAdapter
    private lateinit var citiesRecyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        compositeDisposable = CompositeDisposable()

        viewModel = ViewModelProviders.of(this, viewModelProviderFactory)
            .get(MainActivityViewModel::class.java)

        layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }

        initDatabase()

        insertInitialDataInsideDB()

        initBinding()

        initCitiesAdapter()

        mLifecycleRegistry = LifecycleRegistry(this).apply {
            markState(Lifecycle.State.CREATED)
        }

        handleObserver(binding)

        initCitiesRecyclerView()

        loadInitialDataToCitiesAdapter()
    }




    private fun initDatabase() {
        database = AppDatabase.getAppDataBase(this)!!
    }

    private fun insertInitialDataInsideDB() {
        compositeDisposable.add(
            Completable.fromAction {
                val thread = Thread{
                    with(database) {
                        weatherDao().insert(
                            WeatherModel(
                                UUID.randomUUID().toString(),
                                "Kuala Lumpur"
                            )
                        )
                        weatherDao().insert(
                            WeatherModel(
                                UUID.randomUUID().toString(),
                                "Johor Bahru"
                            )
                        )
                        weatherDao().insert(
                            WeatherModel(
                                UUID.randomUUID().toString(),
                                "George Town"
                            )
                        )
                    }
                }
                thread.start()
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this@MainActivity::successCallBack, this@MainActivity::errorCallback)
        )
    }

    private fun loadInitialDataToCitiesAdapter() {
        citiesListAdapter.clearData()
        val disposable = database.weatherDao().getAllData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                citiesListAdapter.setData(it)
            }, {
                errorCallback(it)
            })
        compositeDisposable.add(disposable)


    }


    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this // To enable Live Data object to update the XML on update
    }

    private fun initCitiesAdapter() {
        citiesListAdapter = CitiesListAdapter(this)
    }

    private fun initCitiesRecyclerView() {
        citiesRecyclerView = findViewById<RecyclerView>(R.id.cities_recycler_view).apply {
            this.layoutManager = this@MainActivity.layoutManager
            this.adapter = this@MainActivity.citiesListAdapter
        }.also {
            it.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0) { // Detects if it is scrolling downwards
                        val lastVisibleItemPosition =
                            (it.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                        if (lastVisibleItemPosition == citiesListAdapter.itemCount - 1) {
//                            if (isNetworkAvailable) {
//                                if (contentSize < totalCount && !isLoading) { // isLoading = false
//                                    loadMoreRecyclerData(pageNumber = ++pageNumber, pageSize = defaultPageSize, numberOfDays = numberOfDays)
//                                }
//                            }
                        }
                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
        }
    }

    private fun handleObserver(binding: ActivityMainBinding) {
        val isNetworkAvailable = true
        binding.viewModel?.let { localViewModel ->
            with(localViewModel) {
                weatherCondition.value = "Cloudy"
                getWeatherDataClick.observe(this@MainActivity, Observer { weatherLiveData ->
                    weatherLiveData.getContentIfNotHandled()?.let {
                        if (isNetworkAvailable) {
                            getWeatherData().observe(this@MainActivity, Observer {
                                it?.let { pair ->
                                    if (pair.first == EnumDataState.SUCCESS.type) {
                                        with(pair.second as WeatherData) {
                                            weatherCondition.value = this.weather[0].main
                                            temparatureInDegreeCelcius.value = String.format(
                                                this@MainActivity.getString(R.string.degree_in_celcius),
                                                this.main.temp
                                            )
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

    private fun successCallBack(){
        Toast.makeText(this,"Success",Toast.LENGTH_LONG).show()
    }
    private fun errorCallback(error: Throwable) {
        if (error is HttpException) {

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
