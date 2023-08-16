package com.example.weatherforecast.utils

import java.text.SimpleDateFormat
import java.util.Date

fun formatDate(timestamp: Int):String{
    val sdf =  SimpleDateFormat("d, EEEE MMMM")
    val date = Date(timestamp.toLong()*1000)

    return sdf.format(date)
}


fun formatDayOfWeek(timestamp: Int):String{
    val sdf =  SimpleDateFormat("EEEE")
    val date = Date(timestamp.toLong()*1000)

    return sdf.format(date)
}


fun formatDateTime(timestamp: Int):String{
    val sdf = SimpleDateFormat("hh:mm:aa")
    val date = Date(timestamp.toLong()*1000)

    return sdf.format(date)
}

fun formatDecimals(item: Double):String{
    var celcius = (item - 32) * 5/9
    return " %.0f".format(celcius)

}

fun formatDecimals2(item: Double):Int{
    var celcius = (item - 32) * 5/9
    return celcius.toInt()

}