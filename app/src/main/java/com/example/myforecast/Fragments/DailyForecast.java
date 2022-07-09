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
import com.example.myforecast.databinding.FragmentDailyForecastBinding;

import java.util.ArrayList;
import java.util.List;


public class DailyForecast extends Fragment {
    private double mLatitude, mLongitude;
    private FragmentDailyForecastBinding mBinding;

    public static DailyForecast newDailyInstance(double mLatitude, double mLongitude) {
        Bundle bundle = new Bundle();
        bundle.putDouble("latitude", mLatitude);
        bundle.putDouble("longitude", mLongitude);

        DailyForecast mFragment = new DailyForecast();
        mFragment.setArguments(bundle);
        return mFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        retrieveLocalization();

        mBinding = FragmentDailyForecastBinding.inflate(getLayoutInflater());
        mBinding.dailyRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.dailyRv.setHasFixedSize(true);

        ForecastViewModel mViewModel = new ViewModelProvider(this).get(ForecastViewModel.class);
        mViewModel.getForecastData(mLatitude, mLongitude).observe(this.getViewLifecycleOwner(), new Observer<List<ForecastModel>>() {
            @Override
            public void onChanged(List<ForecastModel> list) {
                mBinding.dailyRv.setAdapter(new DailyForecastAdapter(list));
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