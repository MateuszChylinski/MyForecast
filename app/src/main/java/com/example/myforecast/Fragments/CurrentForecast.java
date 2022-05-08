package com.example.myforecast.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myforecast.Adapters.HourlyForecastAdapter;
import com.example.myforecast.Model.ForecastModel;
import com.example.myforecast.R;
import com.example.myforecast.ViewModel.ForecastViewModel;

import java.util.ArrayList;
import java.util.List;

public class TestFragment extends Fragment {
    public static final String TAG = "TestFragment";
    private double mLatitude, mLongitude;
    private List<ForecastModel> mTest = new ArrayList<>();

    public TestFragment(double mLatitude, double mLongitude) {
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: TEST " + mLatitude + " " + mLongitude);
        View fragmentView = inflater.inflate(R.layout.fragment_test, container, false);

        RecyclerView recyclerView = fragmentView.findViewById(R.id.hourly_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        ForecastViewModel viewModel = ViewModelProviders.of(this).get(ForecastViewModel.class);

        viewModel.getForecastData(	40.730610, -73.935242).observe(getViewLifecycleOwner(), new Observer<List<ForecastModel>>() {
            @Override
            public void onChanged(List<ForecastModel> forecastModels) {
                mTest.addAll(forecastModels);
                HourlyForecastAdapter adapter = new HourlyForecastAdapter(forecastModels);
                recyclerView.setAdapter(adapter);
            }
        });
        return fragmentView;
    }
}