package com.example.weatherforecast.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherforecast.model.Unit
import com.example.weatherforecast.ui.theme.BgColor
import com.example.weatherforecast.ui.theme.TxtColor
import com.example.weatherforecast.utils.fontFamily
import com.example.weatherforecast.widgets.WeatherAppBar


@Composable
fun SettingsScreen (navController: NavController,
                    settingsViewModel: SettingsViewModel){

    MainScaffold(navController = navController,
                     settingsViewModel )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(navController: NavController,
                 settingsViewModel: SettingsViewModel){
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

        MainContent(modifier = Modifier
                        .padding(paddingValues=paddingValues),
                          settingsViewModel
                        )
    }
}

@Composable
fun MainContent(modifier:Modifier,settingsViewModel: SettingsViewModel){


    var unitToggleState by remember { mutableStateOf(false) }
    val measurementUnit = listOf("Imperial (F)","Metric (C)")
    val choiceFromDB = settingsViewModel.units.collectAsState().value

    val defaultChoice = if(choiceFromDB.isEmpty()) measurementUnit[0]
                        else choiceFromDB[0].unit

    var choiceState by remember { mutableStateOf(defaultChoice)}


    Column(modifier = modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Change the unit of the weather to Fahrenheit or Celsius",
            style= TextStyle(
                textAlign = TextAlign.Center,
                color = TxtColor,
                fontSize = 18.sp,
                fontFamily = fontFamily("Russo One", FontWeight.SemiBold, FontStyle.Normal)
            )
        )

        IconToggleButton(checked = !unitToggleState,
            onCheckedChange ={

                unitToggleState = !it

                choiceState = if (unitToggleState) "Imperial (F)" else "Metric (C)"

            }, modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(5.dp)
                .clip(shape = CircleShape.copy(all = CornerSize(5.dp)))
                .background(TxtColor)
        ) {
                
             Text(text = if(unitToggleState) "Fahrenheit Fº" else "Celsius Cº",
                 style= TextStyle(
                     textAlign = TextAlign.Center,
                     color = BgColor,
                     fontSize = 18.sp,
                     fontFamily = fontFamily("Chakra Petch", FontWeight.SemiBold, FontStyle.Normal)
                 ))
        }

        Button(onClick = {
            settingsViewModel.deleteAllUnits()
            settingsViewModel.insertUnit(Unit(unit = choiceState))
        },
                modifier = Modifier
                    .padding(top = 3.dp)
                    .fillMaxWidth(0.3f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
            Text(text = "Save",
                style= TextStyle(
                    textAlign = TextAlign.Center,
                    color = BgColor,
                    fontSize = 14.sp,
                    fontFamily = fontFamily("Chakra Petch", FontWeight.SemiBold, FontStyle.Normal)
                )
                )
        }
    }
}