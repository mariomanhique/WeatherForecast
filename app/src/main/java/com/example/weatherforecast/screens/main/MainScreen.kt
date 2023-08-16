package com.example.weatherforecast.screens.main

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherforecast.R
import com.example.weatherforecast.data.DataOrException
import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.model.WeatherItem
import com.example.weatherforecast.navigation.WeatherScreens
import com.example.weatherforecast.ui.theme.BgColor
import com.example.weatherforecast.ui.theme.TxtColor
import com.example.weatherforecast.utils.fontFamily
import com.example.weatherforecast.utils.formatDate
import com.example.weatherforecast.utils.formatDateTime
import com.example.weatherforecast.utils.formatDecimals
import com.example.weatherforecast.utils.formatDecimals2
import com.example.weatherforecast.widgets.TextButtonsFilters
import com.example.weatherforecast.widgets.WeatherAppBar
import java.lang.Exception

@Composable
fun MainScreen(navController: NavController,
               weatherViewModel: WeatherViewModel = hiltViewModel()){

    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = weatherViewModel.data.value.loading),
        producer = {
            value = weatherViewModel.getWeatha("Moscow")
        }).value

    if (weatherData.loading==true){
        CircularProgressIndicator()
    }else if(weatherData.data != null){
        MainScaffold(weather = weatherData.data!!, navController = navController)
    }

}

@Composable
fun MainContent(data:Weather,modifier: Modifier,navController: NavController){

    val weatherItem = data.list.first()
    var imageUrl by remember {
        mutableStateOf("https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png")
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {


        //Current day weather resume

            Row(modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .height(100.dp),
                 horizontalArrangement = Arrangement.SpaceBetween) {
                Column() {

                    Text(text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            fontFamily = fontFamily("Russo One", FontWeight.Bold, FontStyle.Normal),
                            fontSize = 60.sp,
                            color = TxtColor
                        )){
                            append(formatDecimals(weatherItem.temp.max)+"º/",)
                        }

                        withStyle(style = SpanStyle(
                            fontFamily = fontFamily("Russo One", FontWeight.Bold, FontStyle.Normal),
                            fontSize = 25.sp,
                            color = TxtColor
                        )){
                            append(formatDecimals(weatherItem.temp.min)+"º",)

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
                WeatherStateImage(imageUrl=imageUrl)

        }

        //Weather details

        HumidityWindPressure(weather=weatherItem) //From components

        //Weather filter + weather per day
        TextButtonsFilters{index ->
            when(index){
                "Today"-> {

                }
                "Tomorrow" ->{

                }
                "Next 10 Days" ->{
                    navController.navigate(WeatherScreens.Next10daysScreen.name+"/${data.city.name}")
                }

            }

        }

        //Hourly Forecast
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
//                .height(60.dp)
                .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SunsetSunriseRaw(
                modifier = Modifier.padding(2.dp),
                weatherIcon = when(formatDecimals2(weatherItem.temp.morn)){
                                        in 10 .. 15 ->{R.drawable.rain}
                                        in 16 .. 18 ->{R.drawable.cloud}
                                        in 19 .. 20->{R.drawable.partialcloudy}
                                        in 21..30->{R.drawable.sunny}
                                        else->{
                                            R.drawable.clearsky
                                        }

                                                                         },
                weatherItemInfo = formatDecimals(weatherItem.temp.morn)+"º",
                weatherItem ="10 am"

            )

            SunsetSunriseRaw(
                modifier = Modifier.padding(2.dp),
                weatherIcon = when(formatDecimals2(weatherItem.temp.day)){
                    in 10 .. 15 ->{R.drawable.rain}
                    in 16 .. 18 ->{R.drawable.cloud}
                    in 19 .. 20->{R.drawable.partialcloudy}
                    in 21..30->{R.drawable.sunny}
                    else->{
                        R.drawable.clearsky
                    }},
                weatherItemInfo = formatDecimals(weatherItem.temp.day)+"º",
                weatherItem ="4 pm"

            )

            SunsetSunriseRaw(
                modifier = Modifier.padding(2.dp),
                weatherIcon =  when(formatDecimals2(weatherItem.temp.eve)){
                    in 10 .. 15 ->{R.drawable.rain}
                    in 16 .. 18 ->{R.drawable.cloud}
                    in 19 .. 20->{R.drawable.partialcloudy}
                    in 21..30->{R.drawable.sunny}
                    else->{
                        R.drawable.clearsky
                    }},
                weatherItemInfo = formatDecimals(weatherItem.temp.eve)+"º",
                weatherItem ="6 PM"

            )
            SunsetSunriseRaw(
                modifier = Modifier.padding(2.dp),
                weatherIcon =  when(formatDecimals2(weatherItem.temp.night)){
                    in 10..15 ->{R.drawable.rain}
                    in 16.. 18 ->{R.drawable.cloud}
                    in 19.. 20->{R.drawable.partialcloudy}
                    in 21..30->{R.drawable.sunny}
                    else->{
                        R.drawable.clearsky
                    }},
                weatherItemInfo = formatDecimals(weatherItem.temp.night)+"º",
                weatherItem ="10 PM"

            )
        }

        ///Maps
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
        ) {

            Card(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(text = "The Map Will be here",

                    )
            }
        }

    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(weather:Weather, navController: NavController){
    Scaffold(topBar = {
        WeatherAppBar(
            title = "${weather.city.name}, ${weather.city.country}",
            timestamp = formatDate(weather.list.first().dt),
            icon = null,
            onNavigationClick = {},
            onSearchClick = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            },
            onMenuClick = {})
    },modifier = Modifier
        .padding(10.dp)
        .fillMaxSize(),
        containerColor = BgColor
    ) { paddingValues ->
        MainContent(data = weather,modifier=Modifier.padding(paddingValues),navController)

    }

}

