package com.example.demoweatherapp.ui.home

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demoweatherapp.R
import com.example.demoweatherapp.core.repository.RemoteDataSourceRepository
import com.example.demoweatherapp.db.entity.WeatherModel
import com.example.demoweatherapp.model.forecast.ForecastCustomizedModel
import com.example.demoweatherapp.model.forecast.ListWeatherInfo
import com.example.demoweatherapp.utils.CustomEventLiveData
import com.example.demoweatherapp.utils.EnumDataState
import com.example.demoweatherapp.utils.PairLocal
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class MainActivityViewModel @Inject constructor(
    private val remoteDataSourceRepository: RemoteDataSourceRepository,
    private val appContext: Application
) :
    ViewModel() {
    /**
     * Live Data getWeatherDataClick event to trigger getting weather data by city name
     **/

    val iconDrawable : ObservableField<Drawable> = ObservableField()

    private val weatherDataClick = MutableLiveData<PairLocal<String, Any>>()
    val weatherData : LiveData<PairLocal<String, Any>>
    get() = weatherDataClick

    private val weatherForecastDataClick = MutableLiveData<PairLocal<String, Any>>()
    val weatherForecastData : LiveData<PairLocal<String, Any>>
    get() = weatherForecastDataClick

    private val weatherDataByLatLongClick = MutableLiveData<PairLocal<String, Any>>()
    val weatherByLatLongData : LiveData<PairLocal<String, Any>>
    get() = weatherDataByLatLongClick

    private val weatherForecastDataByLatLongClick = MutableLiveData<PairLocal<String, Any>>()
    val weatherForecastByLatLongData : LiveData<PairLocal<String, Any>>
    get() = weatherForecastDataByLatLongClick

    private val locationRequestClick = MutableLiveData<Boolean>()
    val locationRequestData : LiveData<Boolean>
    get() = locationRequestClick

    val getShowAllCitiesClick = MutableLiveData<CustomEventLiveData<Boolean>>()

    val cityName = ObservableField<String>()

    val latitude = ObservableField<String>()

    val longitude = ObservableField<String>()

    val temparatureInDegreeCelcius = MutableLiveData<String>()

    val weatherCondition = MutableLiveData<String>()

    init {
        cityName.set("Kuala Lumpur")
        getWeatherData()
        getWeatherForecastData()
    }

    /**
     * Observe weather data click event and triggers getWeatherDataClick event
     * @param none
     * @return none
     **/
    fun openWeatherData() {
        getWeatherData()
        getWeatherForecastData()
    }

    fun openLocationRequest() {
        getLocationRequestClick.value = CustomEventLiveData(true)
    }

    fun openCitiesList() {
        getShowAllCitiesClick.value = CustomEventLiveData(true)
    }

    fun prepareForecastAdapterData(
        list: List<ListWeatherInfo>,
        cityName: String
    ): MutableLiveData<List<ForecastCustomizedModel>> {
        val listOfForecastAdapterLiveData = MutableLiveData<List<ForecastCustomizedModel>>()
        val listOfForecastAdapter = ArrayList<ForecastCustomizedModel>()
        val uniqueDate = ArrayList<Int>()
        for (listWeatherInfo in list) {
            val dateOfTheMonth =
                com.example.demoweatherapp.utils.getDateOfTheMonth(listWeatherInfo.dtTxt)
            if (uniqueDate.contains(dateOfTheMonth)) {
                continue
            }
            uniqueDate.add(dateOfTheMonth)
            val forecastCustomizedModel = ForecastCustomizedModel().apply {
                this.location = cityName
                this.dayOfTheWeek =
                    com.example.demoweatherapp.utils.getDayOfTheWeek(listWeatherInfo.dtTxt)

                this.dateOfTheMonth = dateOfTheMonth

                this.monthOfTheYear =
                    com.example.demoweatherapp.utils.getMonthOfTheYear(listWeatherInfo.dtTxt)
                this.temperature = listWeatherInfo.main.temp.toString()
                this.weatherType = listWeatherInfo.weather[0].main
                this.time = com.example.demoweatherapp.utils.getTime(listWeatherInfo.dtTxt)
            }
            listOfForecastAdapter.add(forecastCustomizedModel)
        }
        listOfForecastAdapterLiveData.value = listOfForecastAdapter

        return listOfForecastAdapterLiveData
    }

    // region weather by city
    private fun getWeatherData() {
        cityName.get()?.let {
            remoteDataSourceRepository.getWeatherDataByCityName(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    weatherDataClick.postValue(PairLocal(EnumDataState.SUCCESS.type, it))
                }, {
                    weatherDataClick.postValue(PairLocal(EnumDataState.SUCCESS.type, it))
                })
        }
    }

    private fun getWeatherForecastData() {
        cityName.get()?.let {
            remoteDataSourceRepository.getWeatherForecastDataByCityName(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    weatherForecastDataClick.postValue(PairLocal(EnumDataState.SUCCESS.type, it))
                }, {
                    weatherForecastDataClick.postValue(PairLocal(EnumDataState.ERROR.type, it))
                })
        }
    }

    // endregion

    // region weather by latlong

    fun getWeatherDataByLatLong() {
        remoteDataSourceRepository.getWeatherDataByLatLong(latitude.get()!!, longitude.get()!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                weatherDataByLatLongClick.postValue(PairLocal(EnumDataState.SUCCESS.type, it))
            }, {
                weatherDataByLatLongClick.postValue(PairLocal(EnumDataState.ERROR.type, it))
            })
    }

    fun getWeatherForecastDataByLatLong(){
        remoteDataSourceRepository.getWeatherForecastDataByLatLong(latitude.get()!!, longitude.get()!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                weatherForecastDataByLatLongClick.postValue(PairLocal(EnumDataState.SUCCESS.type, it))
            }, {
                weatherForecastDataByLatLongClick.postValue(PairLocal(EnumDataState.ERROR.type, it))
            })
    }

    // endregion

    // region database CRUD

    fun insertInitialDataInsideDB() {
        with(remoteDataSourceRepository) {
            insertWeatherDataIntoLocalStorage(
                WeatherModel(
                    UUID.randomUUID().toString(),
                    appContext.getString(R.string.kuala_lumpur)
                )
            )
            insertWeatherDataIntoLocalStorage(
                WeatherModel(
                    UUID.randomUUID().toString(),
                    appContext.getString(R.string.george_town)
                )
            )
            insertWeatherDataIntoLocalStorage(
                WeatherModel(
                    UUID.randomUUID().toString(),
                    appContext.getString(R.string.johor_bahru)
                )
            )
        }
    }

    fun insertDataInsideWeatherDB(model: WeatherModel) {
        remoteDataSourceRepository.insertWeatherDataIntoLocalStorage(model)
    }

    fun getAllLocallyStoredWeatherData(): LiveData<List<WeatherModel>> {
        return remoteDataSourceRepository.getAllLocallyStoredWeatherData()
    }
}