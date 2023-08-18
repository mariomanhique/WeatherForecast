package com.example.weatherforecast.network

import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

interface WeatherAPI {

    @Singleton
    @GET(value="data/2.5/forecast/daily")
    suspend fun getForecast(
        @Query("q")query: String, //mutable
        @Query("units")units:String = "imperial", //can be mutable
        @Query("appid")appid:String=Constants.ApiKey //not mutable
    ):Weather
}