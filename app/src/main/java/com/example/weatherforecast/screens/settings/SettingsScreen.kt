package com.example.weatherforecast.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherforecast.ui.theme.BgColor
import com.example.weatherforecast.ui.theme.TxtColor
import com.example.weatherforecast.utils.fontFamily
import com.example.weatherforecast.widgets.WeatherAppBar


@Composable
fun SettingsScreen (navController: NavController){

    MainScaffold(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(navController: NavController){
    Scaffold(topBar = {
        WeatherAppBar(
            title = "Settings",
            timestamp = "",
            isMainScreenOrRelated = false,
            icon = Icons.Default.ArrowBack,
            onBackClicked = {
                navController.popBackStack()
            },
            onSearchClick = { /*TODO*/ }) {
            
        }
    },
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        containerColor = BgColor
        ) {paddingValues ->
        MainContent(modifier = Modifier.padding(paddingValues=paddingValues))
    }
}

@Composable
fun MainContent(modifier:Modifier){


    var unitToggleState by remember { mutableStateOf(false) }
    val measurementUnit = listOf("Imperial (F)","Metric (C)")
    var choiceState by remember { mutableStateOf("") }

    Column(modifier = modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "",
            style= TextStyle(
                color = TxtColor,
                fontSize = 18.sp,
                fontFamily = fontFamily("Russo One", FontWeight.SemiBold, FontStyle.Normal)
            )
        )

        IconToggleButton(checked = unitToggleState,
            onCheckedChange ={

                unitToggleState = !it

                if (unitToggleState)choiceState = "Imperial (F)" else "Metric (C)"

            } ) {

        }
    }
}