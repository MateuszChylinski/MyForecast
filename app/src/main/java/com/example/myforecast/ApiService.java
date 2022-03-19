package com.example.myforecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
//    TODO - ask user which units he wants (metric,imperial)
    @GET("/data/2.5/forecast")
    Call<ForecastModel> getRecentForecast(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String api_key,
            @Query("units") String units);
}
