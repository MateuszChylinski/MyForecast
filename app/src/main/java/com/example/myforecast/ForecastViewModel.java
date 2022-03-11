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

    private int locationRequestCode;

    public ForecastViewModel(@NonNull Application application) {
        super(application);
        mForecastRepository = ForecastRepository.getInstance();
    }

    public MutableLiveData<List<ForecastModel>> getRecentForecast(){
        mForecastTest = mForecastRepository.getRecentForecast(36, 136, BuildConfig.API_KEY, "metric");
        return mForecastTest;
    }
}
