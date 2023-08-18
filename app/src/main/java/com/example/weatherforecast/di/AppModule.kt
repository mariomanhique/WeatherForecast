package com.example.weatherforecast.di

import android.content.Context
import androidx.room.Room
import com.example.weatherforecast.data.WeatherDao
import com.example.weatherforecast.data.WeatherDatabase
import com.example.weatherforecast.network.WeatherAPI
import com.example.weatherforecast.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOpenWeatherApi():WeatherAPI{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)
    }


    @Singleton
    @Provides
    fun provideFavoriteDatabaseDao(favoriteDatabase: WeatherDatabase):WeatherDao
    = favoriteDatabase.favoriteDao()


    @Singleton
    @Provides
    fun provideFavoriteDatabase(@ApplicationContext context: Context): WeatherDatabase
        = Room.databaseBuilder(
            context=context,
            WeatherDatabase::class.java,
            name = "fav_db").fallbackToDestructiveMigration().build()




}