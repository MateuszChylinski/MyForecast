package com.example.myforecast.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myforecast.Model.ForecastModel;
import com.example.myforecast.R;
import com.example.myforecast.ViewModel.ForecastViewModel;

import java.util.ArrayList;
import java.util.List;


public class TestFragment extends Fragment {
    private static final String TAG = "TestFragment";
    private final double latitude = 51.11711711711712;
    private final double longitude = 17.021132768667826;
    private List<ForecastModel> testList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setup();

        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    private void setup(){
        ForecastViewModel viewModel = ViewModelProviders.of(this).get(ForecastViewModel.class);
        viewModel.getForecastData(latitude, longitude).observe(getViewLifecycleOwner(), new Observer<List<ForecastModel>>() {
            @Override
            public void onChanged(List<ForecastModel> forecastModels) {
                testList.addAll(forecastModels);

                System.out.println("Test@@@@@@");
                System.out.println(forecastModels.get(0).getCurrent().getDateTime());
                System.out.println(forecastModels.get(0).getHourlyForecast().get(0).getDateTime());
                System.out.println(forecastModels.get(0).getDailyForecast().get(0).getDateTime());
            }
        });
    }
}