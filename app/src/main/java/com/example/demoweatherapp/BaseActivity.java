package com.example.demoweatherapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import static com.example.demoweatherapp.utils.ConfigurationsKt.IS_APP_RUNNING_FIRST_TIME;


public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Inject
    SharedPreferences.Editor sharedPreferenceEditor;

    @Inject
    SharedPreferences sharedPreferences;



    public boolean isAppRunningFirstTime() {
        boolean isFirstTime = sharedPreferences.getBoolean(IS_APP_RUNNING_FIRST_TIME, true);
        if (isFirstTime) {
            sharedPreferenceEditor.putBoolean(IS_APP_RUNNING_FIRST_TIME, false).commit();
        }
        return isFirstTime;
    }

    public void onError(Throwable throwable){

    }

}
