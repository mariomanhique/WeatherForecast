package com.example.weatherforecast.repository

import android.util.Log
import com.example.weatherforecast.data.DataOrException
import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.model.WeatherObject
import com.example.weatherforecast.network.WeatherAPI
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherAPI){


    suspend fun getWeather(cityQuery:String): DataOrException<Weather, Boolean, Exception> {

       val response= try {
            api.getForecast(query=cityQuery)

       }catch (e:Exception){

           return DataOrException(exception=e)
        }
        return DataOrException(data = response)
    }
}