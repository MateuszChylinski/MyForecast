package com.example.myforecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/data/2.5/forecast")
    Call<ForecastModel> getRecentForecast(
            @Query("lat") int lat,
            @Query("lon") int lon,
            @Query("appid") String api_key
    );
}
