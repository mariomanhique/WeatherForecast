package com.example.weatherforecast.screens.nextDaysFr

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherforecast.data.DataOrException
import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.screens.main.DayRowForecast
import com.example.weatherforecast.screens.main.HumidityWindPressure
import com.example.weatherforecast.screens.main.WeatherViewModel
import com.example.weatherforecast.screens.settings.SettingsViewModel
import com.example.weatherforecast.ui.theme.BgColor
import com.example.weatherforecast.ui.theme.TxtColor
import com.example.weatherforecast.utils.fontFamily
import com.example.weatherforecast.utils.formatDate
import com.example.weatherforecast.utils.formatDayOfWeek
import com.example.weatherforecast.utils.formatDecimals
import com.example.weatherforecast.widgets.WeatherAppBar
import java.lang.Exception

@Composable
fun NextDaysForecastScreen(navController: NavController,
                           city:String?,
                           weatherViewModel:WeatherViewModel,
                            settingsViewModel:SettingsViewModel){


    val unitFromDb = settingsViewModel.units.collectAsState().value
    var unit by remember {
        mutableStateOf("imperial")
    }
    var isImperial by remember {
        mutableStateOf(false)
    }

    if(unitFromDb.isNotEmpty()){

        unit = unitFromDb[0].unit.split(" ")[0].lowercase()
        isImperial = unit=="imperial"

        val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(loading = true),
            producer = {
                value = weatherViewModel.loadWeather(
                    city=city.toString(),
                    units=unit)
            }).value

        if (weatherData.loading==true){
            CircularProgressIndicator()
        }else if(weatherData.data != null){
            MainScaffold(
                weather = weatherData.data!!,
                navController = navController,
            )
        }

    }
}

@Composable
fun MainContent(modifier: Modifier,weather: Weather){
    val weatherItem = weather.list.first()
    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Row(modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .height(100.dp),
            horizontalArrangement = Arrangement.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        fontFamily = fontFamily("Russo One", FontWeight.Bold, FontStyle.Normal),
                        fontSize = 60.sp,
                        color = TxtColor
                    )
                    ){
                        append(formatDecimals(weatherItem.temp.max) +"º/")
                    }

                    withStyle(style = SpanStyle(
                        fontFamily = fontFamily("Russo One", FontWeight.Bold, FontStyle.Normal),
                        fontSize = 25.sp,
                        color = TxtColor
                    )
                    ){
                        append(formatDecimals(weatherItem.temp.min) +"º")

                    }
                }
                )
                Text(text = weatherItem.weather.first().main,
                    style = TextStyle(
                        fontFamily = fontFamily("Chakra Petch", FontWeight.SemiBold, FontStyle.Normal),
                        fontSize = 15.sp,
                        color = Color.Gray
                    )
                )

            }
//            WeatherStateImage(imageUrl=imageUrl)

        }

        HumidityWindPressure(weather=weatherItem, isImperial = true)//From components

        Column {
            //7 days forecast rows


            weather.list.forEach {
                val imageUrl = "https://openweathermap.org/img/wn/${it.weather[0].icon}.png"

                DayRowForecast(formatDayOfWeek(it.dt), formatDecimals(it.temp.min),
                    formatDecimals(it.temp.max), imageUrl = imageUrl)

            }


        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(weather:Weather,navController: NavController){
    Scaffold(topBar = {
        WeatherAppBar(
            title = "${weather.city.name}, ${weather.city.country}",
            timestamp = formatDate(weather.list.first().dt),
            icon = Icons.Default.ArrowBack,
            isMainScreenOrRelated = false,
            onBackClicked = {
                navController.popBackStack()
            },
            onSearchClick = {},
            onMenuClick = {})
    },modifier = Modifier
        .padding(10.dp)
        .fillMaxSize(),
        containerColor = BgColor
    ) { paddingValues ->
       MainContent(
           modifier = Modifier.padding(paddingValues),
           weather = weather,
        )

    }
}

