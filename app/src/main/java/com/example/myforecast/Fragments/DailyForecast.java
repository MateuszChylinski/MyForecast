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
    private static final String TAG = "DailyForecast";
    private double mLatitude, mLongitude;
    private List<ForecastModel> mData = new ArrayList<>();
    private ForecastViewModel mViewModel;
    private FragmentDailyForecastBinding mBinding;


    public DailyForecast(double mLatitude, double mLongitude) {
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentDailyForecastBinding.inflate(getLayoutInflater());
        mBinding.dailyRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.dailyRv.setHasFixedSize(true);


        mViewModel = ViewModelProviders.of(this).get(ForecastViewModel.class);
        mViewModel.getForecastData(mLatitude, mLongitude).observe(this.getViewLifecycleOwner(), new Observer<List<ForecastModel>>() {
            @Override
            public void onChanged(List<ForecastModel> list) {
                mBinding.dailyRv.setAdapter(new DailyForecastAdapter(list));
            }
        });
        return mBinding.getRoot();
    }
}