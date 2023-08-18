package com.example.weatherforecast.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherforecast.R
import com.example.weatherforecast.ui.theme.BgColor
import com.example.weatherforecast.ui.theme.TxtColor
import com.example.weatherforecast.utils.fontFamily
import com.example.weatherforecast.widgets.WeatherAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController:NavController) {

    Scaffold(topBar = {
        WeatherAppBar(
            title = "About",
            timestamp = "",
            isMainScreenOrRelated = false,
            icon = Icons.Default.ArrowBack,
            onBackClicked = {
                    navController.popBackStack()
            },
            onSearchClick = { /*TODO*/ }) {

        }

    },modifier = Modifier
        .padding(10.dp)
        .fillMaxSize(),
        containerColor = BgColor
    ) { paddingValues ->


        Column(modifier = Modifier
            .padding(paddingValues = paddingValues)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
                Text(text = stringResource(id = R.string.about_string),
                    style=TextStyle(
                        color = TxtColor,
                        fontSize = 18.sp,
                        fontFamily = fontFamily("Russo One", FontWeight.SemiBold, FontStyle.Normal)
                    )
                )

            Text(text = stringResource(id = R.string.api_used),
                style=TextStyle(
                    color = Color.LightGray,
                    fontSize = 15.sp,
                    fontFamily = fontFamily("Chakra Petch", FontWeight.SemiBold, FontStyle.Normal)
                )
            )
        }

    }

}