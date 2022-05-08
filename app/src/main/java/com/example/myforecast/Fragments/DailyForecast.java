package com.example.myforecast.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myforecast.Adapters.DailyForecastAdapter;
import com.example.myforecast.Adapters.HourlyForecastAdapter;
import com.example.myforecast.Model.ForecastModel;
import com.example.myforecast.R;
import com.example.myforecast.ViewModel.ForecastViewModel;

import java.util.ArrayList;
import java.util.List;


public class DailyForecast extends Fragment {
    private static final String TAG = "DailyForecast";
    private double mLatitude, mLongitude;
    private List<ForecastModel> mData = new ArrayList<>();
    private ForecastViewModel mViewModel;


    public DailyForecast(double mLatitude, double mLongitude) {
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_daily_forecast, container, false);

        RecyclerView recyclerView = fragmentView.findViewById(R.id.daily_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        mViewModel = ViewModelProviders.of(this).get(ForecastViewModel.class);
        mViewModel.getForecastData(40.730610, -73.935242).observe(this.getViewLifecycleOwner(), new Observer<List<ForecastModel>>() {
            @Override
            public void onChanged(List<ForecastModel> list) {
                mData.addAll(list);
                DailyForecastAdapter adapter = new DailyForecastAdapter(mData);
                recyclerView.setAdapter(adapter);
            }
        });
        return fragmentView;
    }
}