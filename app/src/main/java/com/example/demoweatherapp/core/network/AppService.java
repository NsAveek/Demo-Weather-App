package com.example.demoweatherapp.core.network;

import com.example.demoweatherapp.model.WeatherData;
import com.example.demoweatherapp.model.forecast.ForecastData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AppService {


    @GET("weather")
    Observable<WeatherData> getWeatherByCityName(@Query("q") String cityName,
                                                 @Query("APPID") String appID,
                                                 @Query("units") String units);
    @GET("weather")
    Observable<WeatherData> getWeatherByLatLong(@Query("lat") String latitude,
                                                @Query("lon") String longitude,
                                                @Query("APPID") String appID,
                                                @Query("units") String units);
    @GET("forecast")
    Observable<ForecastData> getWeatherForecastByCityName(@Query("q") String cityName,
                                                          @Query("APPID") String appID,
                                                          @Query("units") String units);
    @GET("forecast")
    Observable<ForecastData> getWeatherForecastByLatLong(@Query("lat") String latitude,
                                                         @Query("lon") String longitude,
                                                         @Query("APPID") String appID,
                                                         @Query("units") String units);


}
