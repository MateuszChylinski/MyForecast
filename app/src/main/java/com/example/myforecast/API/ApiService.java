package com.example.myforecast.API;

import com.example.myforecast.Model.ForecastModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/data/2.5/onecall")
    Call<ForecastModel> getRecentForecast(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String api_key,
            @Query("units") String units);
}
