package com.example.myforecast.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.myforecast.BuildConfig;
import com.example.myforecast.Model.ForecastModel;
import com.example.myforecast.Repository.ForecastRepository;

import java.util.List;

public class ForecastViewModel extends AndroidViewModel {
    private ForecastRepository mForecastRepository;
    private MutableLiveData<List<ForecastModel>> mForecastTest = new MutableLiveData<>();

    public ForecastViewModel(@NonNull Application application) {
        super(application);
        mForecastRepository = ForecastRepository.getInstance();
    }

    public MutableLiveData<List<ForecastModel>> getForecastData(double latitude, double longitude){
        mForecastTest = mForecastRepository.getApiData(latitude, longitude, BuildConfig.API_KEY, "metric");
        return mForecastTest;
    }
}