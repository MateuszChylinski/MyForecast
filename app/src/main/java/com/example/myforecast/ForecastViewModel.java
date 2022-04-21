package com.example.myforecast;

import android.Manifest;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ForecastViewModel extends AndroidViewModel {
    private ForecastRepository mForecastRepository;
    private MutableLiveData<List<ForecastModel>> mForecastTest = new MutableLiveData<>();

    public ForecastViewModel(@NonNull Application application) {
        super(application);
        mForecastRepository = ForecastRepository.getInstance();
    }

    public MutableLiveData<List<ForecastModel>> getRecentForecast(double latitude, double longitude){
        mForecastTest = mForecastRepository.getRecentForecast(latitude, longitude, BuildConfig.API_KEY, "metric");
        return mForecastTest;
    }
}
