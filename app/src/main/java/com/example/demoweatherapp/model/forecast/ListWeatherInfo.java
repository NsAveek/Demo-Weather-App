
package com.example.demoweatherapp.model.forecast;

import com.example.demoweatherapp.model.Clouds;
import com.example.demoweatherapp.model.Main;
import com.example.demoweatherapp.model.Weather;
import com.example.demoweatherapp.model.Wind;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ListWeatherInfo {

    @SerializedName("clouds")
    private Clouds mClouds;
    @SerializedName("dt")
    private Long mDt;
    @SerializedName("dt_txt")
    private String mDtTxt;
    @SerializedName("main")
    private Main mMain;
    @SerializedName("rain")
    private Rain mRain;
    @SerializedName("sys")
    private Sys mSys;
    @SerializedName("weather")
    private java.util.List<Weather> mWeather;
    @SerializedName("wind")
    private Wind mWind;

    public Clouds getClouds() {
        return mClouds;
    }

    public void setClouds(Clouds clouds) {
        mClouds = clouds;
    }

    public Long getDt() {
        return mDt;
    }

    public void setDt(Long dt) {
        mDt = dt;
    }

    public String getDtTxt() {
        return mDtTxt;
    }

    public void setDtTxt(String dtTxt) {
        mDtTxt = dtTxt;
    }

    public Main getMain() {
        return mMain;
    }

    public void setMain(Main main) {
        mMain = main;
    }

    public Rain getRain() {
        return mRain;
    }

    public void setRain(Rain rain) {
        mRain = rain;
    }

    public Sys getSys() {
        return mSys;
    }

    public void setSys(Sys sys) {
        mSys = sys;
    }

    public java.util.List<Weather> getWeather() {
        return mWeather;
    }

    public void setWeather(java.util.List<Weather> weather) {
        mWeather = weather;
    }

    public Wind getWind() {
        return mWind;
    }

    public void setWind(Wind wind) {
        mWind = wind;
    }

}
