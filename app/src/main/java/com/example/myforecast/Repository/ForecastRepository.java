package com.example.myforecast.Repository;

import androidx.lifecycle.MutableLiveData;

import com.example.myforecast.API.ApiService;
import com.example.myforecast.Model.ForecastModel;
import com.example.myforecast.API.RetrofitBuilder;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastRepository {
    private ApiService mApi;
    private static final MutableLiveData<List<ForecastModel>> liveData = new MutableLiveData<>();
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

//  Make a call via api, and fill "liveData" with JSON response from the server
    public MutableLiveData<List<ForecastModel>> getApiData(double lat, double lon, String apiKey, String units) {
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