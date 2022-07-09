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

import com.example.myforecast.Adapters.HourlyForecastAdapter;
import com.example.myforecast.Model.ForecastModel;
import com.example.myforecast.R;
import com.example.myforecast.ViewModel.ForecastViewModel;
import com.example.myforecast.databinding.FragmentHourlyForecastBinding;
import com.example.myforecast.databinding.HourlyItemsBinding;

import java.util.ArrayList;
import java.util.List;

public class HourlyForecast extends Fragment {
    private  double mLatitude, mLongitude;
    private FragmentHourlyForecastBinding mBinding;

    public static HourlyForecast newHourlyInstance(double mLatitude, double mLongitude) {
        Bundle bundle = new Bundle();
        bundle.putDouble("latitude", mLatitude);
        bundle.putDouble("longitude", mLongitude);

        HourlyForecast mFragment = new HourlyForecast();
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        retrieveLocalization();

        mBinding = FragmentHourlyForecastBinding.inflate(getLayoutInflater());

        mBinding.hourlyRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.hourlyRv.setHasFixedSize(true);

        ForecastViewModel viewModel = new ViewModelProvider(this).get(ForecastViewModel.class);
        viewModel.getForecastData(mLatitude, mLongitude).observe(getViewLifecycleOwner(), new Observer<List<ForecastModel>>() {
            @Override
            public void onChanged(List<ForecastModel> forecastModels) {

                mBinding.hourlyRv.setAdapter(new HourlyForecastAdapter(forecastModels));
            }
        });
        return mBinding.getRoot();
    }

    private void retrieveLocalization(){
        if (getArguments() != null){
            mLatitude = getArguments().getDouble("latitude");
            mLongitude = getArguments().getDouble("longitude");
        }
    }
}