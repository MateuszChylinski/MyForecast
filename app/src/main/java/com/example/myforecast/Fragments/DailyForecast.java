package com.example.myforecast.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.myforecast.R;


public class DailyForecast extends Fragment {
    private static final String TAG = "DailyForecast";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_daily_forecast, container, false);
    }

}