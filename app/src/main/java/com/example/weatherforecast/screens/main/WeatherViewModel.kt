package com.example.weatherforecast.screens.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.weatherforecast.data.DataOrException
import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.repository.weatherRepository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository):ViewModel() {

//    private val _data:MutableState<DataOrException<Weather,Boolean,Exception>>
//    = mutableStateOf(DataOrException(null,true,Exception("")))
//
//    var data =_data


//    private fun getWeather(city:String)=viewModelScope.launch {
//        _data.value.loading=true
//
//        if (city.isEmpty())return@launch // is this is true return nothing
//
//        _data.value=repository.getWeather(cityQuery = city)
//
//        if(_data.value.data.toString().isNotEmpty()) _data.value.loading
//
//        Log.d("Weather", "Weather: ${_data.value.data?.city} ")
//
//    }

    suspend fun loadWeather(city:String,units:String):DataOrException<Weather,Boolean,Exception>{
        return repository.getWeather(cityQuery = city,units=units)
    }


}