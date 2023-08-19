package com.example.weatherforecast.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun formatDate(timestamp: Int):String{
    val sdf =  SimpleDateFormat("d, EEEE MMMM")
    val date = Date(timestamp.toLong()*1000)

    return sdf.format(date)
}


@SuppressLint("SimpleDateFormat")
fun formatDayOfWeek(timestamp: Int):String{
    val sdf =  SimpleDateFormat("EEEE")
    val date = Date(timestamp.toLong()*1000)

    return sdf.format(date)
}

fun formatDecimals(item: Double):String{
    return " %.0f".format(item)

}

fun formatDecimals2(item: Double):Int{

//    val celcius = (item - 32) * 5/9
    return item.toInt()

}