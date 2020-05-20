package com.example.carsomeweatherapp.model.forecast;

public class ForecastCustomizedModel {
    String temperature;
    String weatherType;
    String date;
    String time;
    String dayOfTheWeek;
    String monthOfTheYear;
    int dateOfTheMonth;
    String location;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(String weatherType) {
        this.weatherType = weatherType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(String dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public String getMonthOfTheYear() {
        return monthOfTheYear;
    }

    public void setMonthOfTheYear(String monthOfTheYear) {
        this.monthOfTheYear = monthOfTheYear;
    }

    public int getDateOfTheMonth() {
        return dateOfTheMonth;
    }

    public void setDateOfTheMonth(int dateOfTheMonth) {
        this.dateOfTheMonth = dateOfTheMonth;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
