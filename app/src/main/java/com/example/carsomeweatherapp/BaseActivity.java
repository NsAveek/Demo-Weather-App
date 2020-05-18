package com.example.carsomeweatherapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import dagger.android.AndroidInjection;


public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    public void onError(Throwable throwable){

    }
}
