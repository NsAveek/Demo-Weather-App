
package com.example.carsomeweatherapp.model.forecast;

import com.example.carsomeweatherapp.model.Coord;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class City {

    @SerializedName("coord")
    private Coord mCoord;
    @SerializedName("country")
    private String mCountry;
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("population")
    private Long mPopulation;
    @SerializedName("sunrise")
    private Long mSunrise;
    @SerializedName("sunset")
    private Long mSunset;
    @SerializedName("timezone")
    private Long mTimezone;

    public Coord getCoord() {
        return mCoord;
    }

    public void setCoord(Coord coord) {
        mCoord = coord;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Long getPopulation() {
        return mPopulation;
    }

    public void setPopulation(Long population) {
        mPopulation = population;
    }

    public Long getSunrise() {
        return mSunrise;
    }

    public void setSunrise(Long sunrise) {
        mSunrise = sunrise;
    }

    public Long getSunset() {
        return mSunset;
    }

    public void setSunset(Long sunset) {
        mSunset = sunset;
    }

    public Long getTimezone() {
        return mTimezone;
    }

    public void setTimezone(Long timezone) {
        mTimezone = timezone;
    }

}
