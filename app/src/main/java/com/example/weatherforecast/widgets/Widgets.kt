package com.example.weatherforecast.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.ElevationOverlay
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherforecast.R
import com.example.weatherforecast.ui.theme.TxtColor
import com.example.weatherforecast.utils.fontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(title:String,
                timestamp:String,
                icon: ImageVector?,
                isMainScreen:Boolean=true,
                isSearchScreen:Boolean=false,
                onNavigationClick:() -> Unit,
                onSearchClick:()-> Unit,
                onMenuClick:()-> Unit){
    //Country/City and time, year and week day
//    TopAppBar(backgroundColor = Color.Transparent,
//                elevation = 0.dp ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            if(icon != null){
                Card(modifier = Modifier,
                    colors = CardDefaults.cardColors(containerColor = Color(0xff202328))) {
//
                    Image(imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(30.dp)
                            .clickable {
                            onNavigationClick.invoke()
                        },alpha=0.7f, colorFilter = ColorFilter.tint(Color.LightGray))
                }

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(0.dp)
            ) {

                Text(text = "$title",
                    color = TxtColor,
                    style = TextStyle(
                                      fontSize = 20.sp,
                                        letterSpacing = 1.sp,
                                        fontFamily = fontFamily("Russo One",FontWeight.Bold, FontStyle.Normal)
                    )
                )
                Text(text = "$timestamp",
                    color = Color.Gray,
                    style=TextStyle(
                        fontSize = 15.sp,
                        fontFamily = fontFamily("Chakra Petch", FontWeight.SemiBold, FontStyle.Normal)
                    )
                )
            }

            if(!isSearchScreen){
                if (isMainScreen){
                    AsyncImage(
                        model = R.drawable.search,
                        contentDescription = "",
                        modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            onSearchClick.invoke()
                        },
                        alpha = 0.6f)
                } else if (!isSearchScreen){

                }
                Card(modifier = Modifier,
                    colors = CardDefaults.cardColors(containerColor = Color(0xff202328))) {
                    AsyncImage(
                        model = R.drawable.menu,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(8.dp)
                            .size(30.dp)
                            .clickable {
                                onMenuClick.invoke()
                            }
                        ,
                        alpha=0.6f)
                }

            }


        }

}

@Composable
fun TextButtonsFilters(onTextClicked:(String)->Unit){
    val list = listOf("Today","Tomorrow","Next 10 Days")
    var colorInt by remember {
        mutableStateOf(0xFF625b71)
    }

    var buttonState by remember {
        mutableStateOf<Int>(0)
    }

    val updateText: (Int) -> Unit= remember {
        //This lambda allow us to have an integer to control our index to check the correct choice

        {
            buttonState=it
        }
    }

    Row() {
        list.forEachIndexed { index, text ->

            Column() {
                Text(text = text,
                    style=TextStyle(
                        color = if (buttonState == index) TxtColor else Color.Gray,
                        fontSize = 15.sp,
                        fontFamily = fontFamily("Chakra Petch", FontWeight.SemiBold, FontStyle.Normal)
                    ),
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .selectable(
                            selected = buttonState == index,
                            enabled = true
                        ) {
                            updateText(index)
                            onTextClicked(text)
                        }
                )
            }

        }
    }

}