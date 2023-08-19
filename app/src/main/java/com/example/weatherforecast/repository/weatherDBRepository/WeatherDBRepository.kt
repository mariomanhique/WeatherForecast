package com.example.weatherforecast.repository.weatherDBRepository

import com.example.weatherforecast.data.WeatherDao
import com.example.weatherforecast.model.Favorite
import com.example.weatherforecast.model.Unit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherDBRepository @Inject constructor(private val database: WeatherDao){

    fun getFavorites():Flow<List<Favorite>>
    = database.getFavorites().flowOn(Dispatchers.IO).conflate()

    fun getUnit():Flow<List<Unit>>
    =database.getUnits().flowOn(Dispatchers.IO).conflate()

    suspend fun getFavoriteByCity(city: String){
        database.getFavoriteByCity(city = city)
    }

    suspend fun insert(favorite: Favorite)
    = database.insertFavorite(favorite = favorite)

    suspend fun delete(favorite: Favorite)
    =database.delete(favorite = favorite)

    suspend fun insertUnit(unit: Unit)
    = database.insertUnity(unit=unit)

    suspend fun updateUnit(unit: Unit)
    = database.updateUnit(unit=unit)

    suspend fun deleteUnit(unit: Unit)
            =database.deleteUnit(unit = unit)

    suspend fun deleteAllUnits()=database.deleteAllUnit()



}