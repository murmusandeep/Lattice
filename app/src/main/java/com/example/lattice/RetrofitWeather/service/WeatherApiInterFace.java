package com.example.lattice.RetrofitWeather.service;

import com.example.lattice.RetrofitWeather.models.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiInterFace {

    @GET("current.json?key=b8f48217200e44b3a8e84021211508&aqi=no")
    Call<WeatherData> getWeatherData(@Query("q") String name);

}
