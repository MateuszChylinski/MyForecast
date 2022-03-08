package com.example.myforecast;

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

    private static ForecastRepository forecastRepository;

    public static ForecastRepository getInstance(){
        if (forecastRepository == null){
            forecastRepository = new ForecastRepository();
        }
        return forecastRepository;
    }

    public ForecastRepository(){
        mApi = RetrofitBuilder.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<List<ForecastModel>> getRecentForecast(int lat, int lon, String apiKey){
        Call<ForecastModel> forecast = mApi.getRecentForecast(lat, lon, apiKey);
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
