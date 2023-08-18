package com.example.weatherforecast.screens.main


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherforecast.R
import com.example.weatherforecast.model.WeatherItem
import com.example.weatherforecast.ui.theme.TxtColor
import com.example.weatherforecast.utils.fontFamily

@Composable
fun CurrentForecast(
                modifier: Modifier=Modifier,
                weatherIcon:Int,
                weatherItemInfo:String,
                weatherItem: String,
                ){
    Column(modifier = modifier.padding(4.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally) {

       AsyncImage(model = weatherIcon,
                  contentDescription ="",
                    modifier = Modifier
                        .padding(5.dp)
                        .size(30.dp)
                   )
        Text(text = weatherItemInfo,
            style= TextStyle(
                color = TxtColor,
                fontSize = 19.sp,
                fontFamily = fontFamily("Chakra Petch", FontWeight.SemiBold, FontStyle.Normal),

            )
        )

        Text(text = weatherItem,
            style=TextStyle(
                color = Color.Gray,
                fontSize = 16.sp,
                fontFamily = fontFamily("Chakra Petch", FontWeight.SemiBold, FontStyle.Normal)
            ))
    }
}


@Composable
fun WeatherStateImage(imageUrl:String){
    AsyncImage(model = imageUrl,
        contentDescription = "",
        modifier = Modifier.size(90.dp).scale(1.5f))

}


@Composable
fun SunsetSunriseRaw(
    modifier: Modifier=Modifier,
    weatherIcon:Int,
    weatherItemInfo:String,
    weatherItem: String,
){

    Card(modifier = modifier
        .width(100.dp)
        .padding(3.dp),
        shape = CircleShape.copy(all= CornerSize(20.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xff202328))) {

    Column(modifier = modifier
        .padding(4.dp)
        .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = weatherItem,
            style=TextStyle(
                color = Color.Gray,
                fontSize = 16.sp,
                fontFamily = fontFamily("Chakra Petch", FontWeight.SemiBold, FontStyle.Normal)
            ), modifier = modifier)

        AsyncImage(model = weatherIcon,
            contentDescription ="",
            modifier = modifier
                .padding(1.dp)
                .size(30.dp),
                contentScale = ContentScale.Fit
        )
        Text(text = weatherItemInfo,
            style= TextStyle(
                color = TxtColor,
                fontSize = 19.sp,
                fontFamily = fontFamily("Chakra Petch", FontWeight.SemiBold, FontStyle.Normal)),
            modifier = modifier
        )


    }
    }

}



@Composable
fun HumidityWindPressure(weather: WeatherItem){
    Column(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(colors = CardDefaults.cardColors(Color(0xff202328))) {
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CurrentForecast(
                    weatherIcon = R.drawable.wind,
                    weatherItemInfo = "${weather.speed} m/p",
                    weatherItem = "Wind",
                )

                CurrentForecast(modifier=Modifier.padding(start = 5.dp, end = 5.dp),
                    weatherIcon = R.drawable.water,
                    weatherItemInfo = "${weather.humidity}%",
                    weatherItem = "Humidity",
                )

                CurrentForecast(
                    weatherIcon = R.drawable.rain,
                    weatherItemInfo = "${weather.rain}%",
                    weatherItem = "Rain",
                )
            }
        }
    }

}

@Composable
fun DayRowForecast(dayOfWeek:String,minTemp:String,maxTemp:String,imageUrl: String){
    Row (modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()
        .height(45.dp),horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
        ){

//        val gradient = Brush.linearGradient(listOf( Color(0xFF24F3A1), Color(0xFF0DB3DB)))
        Text(text = dayOfWeek,
            color = TxtColor,
            fontSize = 19.sp,
            textAlign = TextAlign.Left,
            fontFamily = fontFamily("Chakra Petch", FontWeight.SemiBold, FontStyle.Normal),
            modifier = Modifier.width(110.dp))
        Text(text = minTemp+"º",
            color = Color.Gray,
            fontSize = 19.sp,
            fontFamily = fontFamily("Chakra Petch", FontWeight.SemiBold, FontStyle.Normal))

        Text(text = maxTemp+"º",
            color = TxtColor,
            fontSize = 19.sp,
            fontFamily = fontFamily("Chakra Petch", FontWeight.SemiBold, FontStyle.Normal))


        AsyncImage(model = imageUrl,
            contentDescription ="",
            modifier = Modifier
                .size(60.dp)
        )

    }


}