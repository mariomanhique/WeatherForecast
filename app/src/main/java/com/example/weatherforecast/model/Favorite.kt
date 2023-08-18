package com.example.weatherforecast.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_tbl")
data class Favorite(
    @PrimaryKey
    @ColumnInfo(name = "city")
    val city:String,
    @ColumnInfo(name = "country")
    val country:String
)