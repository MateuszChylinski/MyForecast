package com.example.myforecast;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastRepository {
    private ApiService mApi;
    private static final MutableLiveData<List<ForecastModel>> liveData = new MutableLiveData<>();
    private static final String TAG = "ForecastRepository";

    private int mLatitude, mLongitude;

    private static ForecastRepository forecastRepository;

    public static ForecastRepository getInstance() {
        if (forecastRepository == null) {
            forecastRepository = new ForecastRepository();
        }
        return forecastRepository;
    }

    public ForecastRepository() {
        mApi = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);
    }

    //TODO Get user location - latitude, longitude
    public MutableLiveData<List<ForecastModel>> getRecentForecast(double lat, double lon, String apiKey, String units) {
        Call<ForecastModel> forecast = mApi.getRecentForecast(lat, lon, apiKey, units);
        forecast.enqueue(new Callback<ForecastModel>() {
            @Override
            public void onResponse(Call<ForecastModel> call, Response<ForecastModel> response) {
                liveData.setValue(Collections.singletonList(response.body()));
            }

            @Override
            public void onFailure(Call<ForecastModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return liveData;
    }
}