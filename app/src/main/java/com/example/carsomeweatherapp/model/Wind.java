
package com.example.carsomeweatherapp.model;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Wind {

    @SerializedName("speed")
    private Double mSpeed;

    public Double getSpeed() {
        return mSpeed;
    }

    public void setSpeed(Double speed) {
        mSpeed = speed;
    }

}
