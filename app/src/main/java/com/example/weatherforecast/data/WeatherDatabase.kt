package com.example.weatherforecast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherforecast.model.Favorite
import com.example.weatherforecast.model.Unit

@Database(entities = [Favorite::class,Unit::class], version = 2, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun favoriteDao(): WeatherDao

}