package com.example.myforecast.Date;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertData {

    public String convertDate(long unixDate) {
        Date date = new Date(unixDate * 1000L);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("EEE, HH:mm");
        return format.format(date);
    }

    public String convertSunriseSunset(long unixDate) {
        Date date = new Date(unixDate * 1000L);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    public float convertPop(double pop){
        return (int) pop*100;
    }
}