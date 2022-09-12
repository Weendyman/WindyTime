package com.example.windtime3;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("forecast/")
    Call<Root> getCurrentWeatherData(@Query("lat") String query_lat,@Query("lon") String query_lon,@Query("APPID") String appId,@Query("units") String metric);
}
