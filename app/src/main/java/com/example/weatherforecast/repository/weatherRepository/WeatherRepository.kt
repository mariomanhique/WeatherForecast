package com.example.weatherforecast.repository.weatherRepository

import com.example.weatherforecast.data.DataOrException
import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.network.WeatherAPI
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherAPI){


    suspend fun getWeather(cityQuery:String,units:String): DataOrException<Weather, Boolean, Exception> {

       val response= try {
            api.getForecast(query=cityQuery, units = units)

       }catch (e:Exception){

           return DataOrException(exception=e)
        }
        return DataOrException(data = response)
    }
}