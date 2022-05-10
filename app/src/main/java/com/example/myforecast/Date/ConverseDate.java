package com.example.myforecast.Date;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConverseDate {

    public String converseDate(long unixDate) {
        Date date = new Date(unixDate * 1000L);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("EEE, HH:mm");
        return format.format(date);
    }

    public String converseSunriseSunset(long unixDate) {
        Date date = new Date(unixDate * 1000L);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }
}
