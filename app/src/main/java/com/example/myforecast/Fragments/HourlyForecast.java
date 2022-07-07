package com.example.myforecast.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myforecast.Adapters.HourlyForecastAdapter;
import com.example.myforecast.Model.ForecastModel;
import com.example.myforecast.R;
import com.example.myforecast.ViewModel.ForecastViewModel;
import com.example.myforecast.databinding.FragmentHourlyForecastBinding;
import com.example.myforecast.databinding.HourlyItemsBinding;

import java.util.ArrayList;
import java.util.List;

public class HourlyForecast extends Fragment {
    private static final String TAG = "HourlyForecast";
    private final double mLatitude, mLongitude;
    FragmentHourlyForecastBinding mBinding;

    public HourlyForecast(double mLatitude, double mLongitude) {

        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentHourlyForecastBinding.inflate(getLayoutInflater());

        mBinding.hourlyRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.hourlyRv.setHasFixedSize(true);
//        TODO DEPRECATED
        ForecastViewModel viewModel = ViewModelProviders.of(this).get(ForecastViewModel.class);
        viewModel.getForecastData(mLatitude, mLongitude).observe(getViewLifecycleOwner(), new Observer<List<ForecastModel>>() {
            @Override
            public void onChanged(List<ForecastModel> forecastModels) {

                mBinding.hourlyRv.setAdapter(new HourlyForecastAdapter(forecastModels));
            }
        });
        return mBinding.getRoot();
    }
}