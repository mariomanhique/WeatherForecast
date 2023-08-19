package com.example.weatherforecast.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weatherforecast.model.Favorite
import com.example.weatherforecast.model.Unit
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("select * from fav_tbl")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("select * from fav_tbl where city=:city")
    suspend fun getFavoriteByCity(city:String):Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun delete(favorite: Favorite)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnity(unit: Unit)

    @Query("select * from settings_tbl")
    fun getUnits(): Flow<List<Unit>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit:Unit)

    @Delete
    suspend fun deleteUnit(unit: Unit)

    @Query("delete from settings_tbl")
    suspend fun deleteAllUnit()
}