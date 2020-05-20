package com.example.carsomeweatherapp.ui.home

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import aveek.com.management.ui.db.AppDatabase
import com.example.carsomeweatherapp.R
import com.example.carsomeweatherapp.core.events.ListenToCityAdapterItemCall
import com.example.carsomeweatherapp.core.events.ListenToJSONCityAdapterItemCall
import com.example.carsomeweatherapp.databinding.ActivityMainBinding
import com.example.carsomeweatherapp.db.WeatherModel
import com.example.carsomeweatherapp.model.WeatherData
import com.example.carsomeweatherapp.model.forecast.ForecastCustomizedModel
import com.example.carsomeweatherapp.model.forecast.ForecastData
import com.example.carsomeweatherapp.network.NetworkActivity
import com.example.carsomeweatherapp.ui.cities.CitiesBottomSheetFragment
import com.example.carsomeweatherapp.ui.home.cities.adapter.CitiesListAdapter
import com.example.carsomeweatherapp.ui.home.cities.adapter.WeatherForecastListAdapter
import com.example.carsomeweatherapp.utils.EnumDataState
import com.example.carsomeweatherapp.viewModel.ViewModelProviderFactory
import com.google.android.gms.location.*
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import retrofit2.HttpException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

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
    private lateinit var citiesListLayoutManager: LinearLayoutManager

    private lateinit var weatherForecastListAdapter: WeatherForecastListAdapter
    private lateinit var weatherForecastRecyclerView: RecyclerView
    private lateinit var weatherForecastListLayoutManager: LinearLayoutManager

    private lateinit var jsonString: String
    private lateinit var jsonCities: JSONObject

    private lateinit var permissions: ArrayList<String>
    private lateinit var mFusedLocationClient : FusedLocationProviderClient

    companion object {
        private val PERMISSION_ID = 44
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        compositeDisposable = CompositeDisposable()

        jsonString = loadCitiesFromJson("cities.json")

        try {
            jsonCities = JSONObject(jsonString)
        } catch (e: Exception) {

        }

        locationHandler()


        viewModel = ViewModelProviders.of(this, viewModelProviderFactory)
            .get(MainActivityViewModel::class.java)


        citiesListLayoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        weatherForecastListLayoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }

        initDatabase()

        if (isAppRunningFirstTime) {
            insertInitialDataInsideDB()
        }

        initBinding()

        initCitiesAdapter()

        initWeatherForecastListAdapter()

        mLifecycleRegistry = LifecycleRegistry(this).apply {
            markState(Lifecycle.State.CREATED)
        }

        handleObserver(binding)


        initCitiesRecyclerView()

        initWeatherForecastRecyclerView()

        loadInitialDataToCitiesAdapter()

        with(viewModel) {
            this.cityName.set(getString(R.string.kuala_lumpur))
            this.openWeatherData() // initiate data load
        }
    }

    private fun locationHandler() {
        permissions = ArrayList<String>()

        permissions.add(ACCESS_FINE_LOCATION)
        permissions.add(ACCESS_COARSE_LOCATION)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

//        getLastLocation()
    }

    private fun loadCitiesFromJson(textFileName: String): String {
        var strJSON: String
        var buf: StringBuilder = StringBuilder()
        val json: InputStream
        try {
            json = this.assets.open(textFileName)

            val inData: BufferedReader = BufferedReader(InputStreamReader(json, "UTF-8"))

            while (inData.readLine() != null) {
                strJSON = inData.readLine()

                buf.append(strJSON)
            }
            inData.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return buf.toString()

    }

    private fun initWeatherForecastRecyclerView() {
        weatherForecastRecyclerView = findViewById<RecyclerView>(R.id.rcv_future_weather).apply {
            this.layoutManager = this@MainActivity.weatherForecastListLayoutManager
            this.adapter = this@MainActivity.weatherForecastListAdapter
        }.also {
            it.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) { // Detects if it is scrolling downwards
                        val lastVisibleItemPosition =
                            (it.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                        if (lastVisibleItemPosition == weatherForecastListAdapter.itemCount - 1) {
                        }
                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
        }
    }

    private fun initDatabase() {
        database = AppDatabase.getAppDataBase(this)!!
    }

    private fun insertInitialDataInsideDB() {
        compositeDisposable.add(
            Completable.fromAction {
                val thread = Thread {
                    with(database) {
                        weatherDao().insert(
                            WeatherModel(
                                UUID.randomUUID().toString(),
                                getString(R.string.kuala_lumpur)
                            )
                        )
                        weatherDao().insert(
                            WeatherModel(
                                UUID.randomUUID().toString(),
                                getString(R.string.george_town)
                            )
                        )
                        weatherDao().insert(
                            WeatherModel(
                                UUID.randomUUID().toString(),
                                getString(R.string.johor_bahru)
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

    private fun insertDataToDatabase(data: String) {
        compositeDisposable.add(
            Completable.fromAction {
                val thread = Thread {
                    with(database) {
                        this.weatherDao()
                            .insert(WeatherModel(UUID.randomUUID().toString(), cityName = data))
                    }
                }
                thread.start()
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this@MainActivity::successInsertionData, this@MainActivity::errorCallback)
        )
    }


    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this // To enable Live Data object to update the XML on update
    }

    private fun initCitiesAdapter() {
        citiesListAdapter = CitiesListAdapter(this)
    }

    private fun initWeatherForecastListAdapter() {
        weatherForecastListAdapter = WeatherForecastListAdapter(this, viewModelProviderFactory)
    }

    private fun initCitiesRecyclerView() {
        citiesRecyclerView = findViewById<RecyclerView>(R.id.cities_recycler_view).apply {
            this.layoutManager = this@MainActivity.citiesListLayoutManager
            this.adapter = this@MainActivity.citiesListAdapter
        }.also {
            it.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) { // Detects if it is scrolling downwards
                        val lastVisibleItemPosition =
                            (it.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                        if (lastVisibleItemPosition == citiesListAdapter.itemCount - 1) {
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

        binding.viewModel?.let { localViewModel ->
            with(localViewModel) {
                getShowAllCitiesClick.observe(this@MainActivity, Observer { allCities ->
                    CitiesBottomSheetFragment.getCitiesBottomSheetFragment().apply {
                        show(supportFragmentManager,null)
                    }
                })
                getLocationRequestClick.observe(this@MainActivity, Observer { locationRequest ->
                    locationRequest.getContentIfNotHandled()?.let {
                        if (it) {
                            getLocation()
                        }
                    }
                })
                getWeatherDataClick.observe(this@MainActivity, Observer { weatherLiveData ->
                    weatherLiveData.getContentIfNotHandled()?.let {
                        if (isNetworkAvailable()) {
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
                            getWeatherForecastData().observe(this@MainActivity, Observer {
                                it?.let { pair ->
                                    if (pair.first == EnumDataState.SUCCESS.type) {
                                        with(pair.second as ForecastData) {
                                            weatherForecastListAdapter.clearData()
                                            weatherForecastListAdapter.setData(
                                                prepareForecastAdapter(this)
                                            )
                                        }
                                    } else {
                                        with(pair.second as Throwable) {
                                            errorCallback(this)
                                        }
                                    }
                                }
                            })
                        } else {
                            networkNotAvailable()
                        }
                    }
                })
            }
        }
    }

    private fun prepareForecastAdapter(forecastData: ForecastData): List<ForecastCustomizedModel> {

        val listOfForecastAdapter = ArrayList<ForecastCustomizedModel>()
        val uniqueDate = ArrayList<Int>()
        val list = forecastData.list

        for (listWeatherInfo in list) {
            val dateOfTheMonth = com.example.carsomeweatherapp.utils.getDateOfTheMonth(listWeatherInfo.dtTxt)
            if(uniqueDate.contains(dateOfTheMonth)){
                continue
            }
            uniqueDate.add(dateOfTheMonth)
            val forecastCustomizedModel = ForecastCustomizedModel().apply {
                this.location = forecastData.city.name
                this.dayOfTheWeek = com.example.carsomeweatherapp.utils.getDayOfTheWeek(listWeatherInfo.dtTxt)

                this.dateOfTheMonth = dateOfTheMonth

                this.monthOfTheYear =
                    com.example.carsomeweatherapp.utils.getMonthOfTheYear(listWeatherInfo.dtTxt)
                this.temperature = listWeatherInfo.main.temp.toString()
                this.weatherType = listWeatherInfo.weather[0].main
                this.time = com.example.carsomeweatherapp.utils.getTime(listWeatherInfo.dtTxt)
            }
            listOfForecastAdapter.add(forecastCustomizedModel)
        }
        return listOfForecastAdapter
    }

    private fun successCallBack() {
        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
    }
    private fun successInsertionData() {
        citiesListAdapter.notifyItemInserted(citiesListAdapter.itemCount-1)
    }

    private fun errorCallback(error: Throwable) {
        if (error is HttpException) {
            Toast.makeText(this, "Error${error.response()}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onStart() {
        super.onStart()
        mLifecycleRegistry.markState(Lifecycle.State.STARTED)
        EventBus.getDefault().register(this)
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
        EventBus.getDefault().unregister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED)
        compositeDisposable.dispose()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ListenToCityAdapterItemCall) {
        with(binding) {
            viewModel?.let {
                it.cityName?.let { valueName ->
                    valueName.set(event.getMessage())
                }
                it.openWeatherData()
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ListenToJSONCityAdapterItemCall) {
        insertDataToDatabase(data = event.getMessage())
    }

    fun processRequest(binding: ActivityMainBinding, cityNameData: String) {

        binding.viewModel?.let { localViewModel ->
            with(localViewModel) {
                openWeatherData()
                getWeatherDataClick.observe(this@MainActivity, Observer { weatherLiveData ->
                    weatherLiveData.getContentIfNotHandled()?.let {
                        if (isNetworkAvailable()) {
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
                            networkNotAvailable()
                        }
                    }
                })
            }
        }
    }

    private fun getWeatherDataByLocation(latitude : String, longitude : String){
        if (isNetworkAvailable()) {
            with(viewModel){
                this.latitude.set(latitude)
                this.longitude.set(longitude)
                getWeatherDataByLatLong().observe(this@MainActivity, Observer {
                    it?.let { pair ->
                        if (pair.first == EnumDataState.SUCCESS.type) {
                            with(pair.second as WeatherData) {

                                cityName.set(this.name)

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
                getWeatherForecastDataByLatLong().observe(this@MainActivity, Observer {
                    it?.let { pair ->
                        if (pair.first == EnumDataState.SUCCESS.type) {
                            with(pair.second as ForecastData) {
                                weatherForecastListAdapter.clearData()
                                weatherForecastListAdapter.setData(
                                    prepareForecastAdapter(this)
                                )
                            }
                        } else {
                            with(pair.second as Throwable) {
                                errorCallback(this)
                            }
                        }
                    }
                })
            }
        } else {
            networkNotAvailable()
        }
    }

    private fun getLocation() {
        getLastLocation()
    }


    @SuppressLint("MissingPermission")
    private fun getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this){
                    task ->
                    if (task.isSuccessful) {
                        val location = task.result
                        if (location == null) {
                            requestNewLocationData()
                        } else {
                            getWeatherDataByLocation(location.latitude.toString(),location.longitude.toString())
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Exception occurred, no location detected",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }


    @SuppressLint("MissingPermission")
    private fun requestNewLocationData(){

        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        )

    }

    private var mLocationCallback = object : LocationCallback(){
        override fun onLocationResult( locationResult : LocationResult) {
            val mLastLocation = locationResult.lastLocation
            getWeatherDataByLocation(locationResult.lastLocation.latitude.toString(),locationResult.lastLocation.longitude.toString())
        }
    }

    private fun checkPermissions() : Boolean{
        if (ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        val array = arrayOfNulls<String>(permissions.size)
        ActivityCompat.requestPermissions(
                this,
                array,
                PERMISSION_ID
        )
    }

    private fun isLocationEnabled() : Boolean{
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        )
    }


    override fun onRequestPermissionsResult(requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            }
        }
    }

}
