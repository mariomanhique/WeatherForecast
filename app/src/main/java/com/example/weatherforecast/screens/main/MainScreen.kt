package com.example.weatherforecast.screens.main

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
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
import com.example.weatherforecast.R
import com.example.weatherforecast.data.DataOrException
import com.example.weatherforecast.model.Favorite
import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.navigation.WeatherScreens
import com.example.weatherforecast.screens.favorites.WeatherFavoriteViewModel
import com.example.weatherforecast.ui.theme.BgColor
import com.example.weatherforecast.ui.theme.TxtColor
import com.example.weatherforecast.utils.fontFamily
import com.example.weatherforecast.utils.formatDate
import com.example.weatherforecast.utils.formatDecimals
import com.example.weatherforecast.utils.formatDecimals2
import com.example.weatherforecast.widgets.ShowSettingDialog
import com.example.weatherforecast.widgets.TextButtonsFilters
import com.example.weatherforecast.widgets.WeatherAppBar
import java.lang.Exception

@Composable
fun MainScreen(navController: NavController,
               city:String?,
               weatherViewModel: WeatherViewModel,
               favoriteViewModel: WeatherFavoriteViewModel){

    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = weatherViewModel.data.value.loading),
        producer = {
            value = weatherViewModel.loadWeather(city=city!!)
        }).value

    if (weatherData.loading==true){
        CircularProgressIndicator()
    }else if(weatherData.data != null){
        MainScaffold(weather = weatherData.data!!, navController = navController,favoriteViewModel)
    }

}

@Composable
fun MainContent(data:Weather,modifier: Modifier,navController: NavController){

    val weatherItem = data.list.first()
    val imageUrl by remember {
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
                Column{

                    Text(text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            fontFamily = fontFamily("Russo One", FontWeight.Bold, FontStyle.Normal),
                            fontSize = 60.sp,
                            color = TxtColor
                        )){
                            append(formatDecimals(weatherItem.temp.max)+"º/")
                        }

                        withStyle(style = SpanStyle(
                            fontFamily = fontFamily("Russo One", FontWeight.Bold, FontStyle.Normal),
                            fontSize = 25.sp,
                            color = TxtColor
                        )){
                            append(formatDecimals(weatherItem.temp.min)+"º")

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

        //Weather filter
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
fun MainScaffold(weather:Weather,
                 navController: NavController,
                 favoriteViewModel: WeatherFavoriteViewModel){
    val showDialog = remember {
        mutableStateOf(false)
    }

    if(showDialog.value){
        ShowSettingDialog(showDialog=showDialog,navController=navController)
    }

    val city = weather.city.name
    val countryCode = weather.city.country

    val  fav = favoriteViewModel.favList.collectAsState().value
    val isFavExist = remember {
        mutableStateOf(
            fav.any {
                city==it.city
            }
        )
    }

    Scaffold(topBar = {
        WeatherAppBar(
            title = "$city,$countryCode",
            timestamp = formatDate(weather.list.first().dt),
            icon = null,
            favState = isFavExist,
            onBackClicked = {},
            onFavClicked = {favState->

                if (!favState) {
                    favoriteViewModel.delete(
                        Favorite(
                            city,
                            countryCode
                        )
                    )
                } else {
                    favoriteViewModel.insert(
                        Favorite(
                            city,
                            countryCode
                        )
                    )
                }

            },
            onSearchClick = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            },
            onMenuClick = {
                showDialog.value = true
            })
    },modifier = Modifier
        .padding(10.dp)
        .fillMaxSize(),
        containerColor = BgColor
    ) { paddingValues ->
        MainContent(data = weather,modifier=Modifier.padding(paddingValues),navController)

    }

}

