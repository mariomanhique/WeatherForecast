package com.example.weatherforecast.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherforecast.R
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.scale
import com.example.weatherforecast.navigation.WeatherScreens
import kotlinx.coroutines.delay

@Composable
fun WeatherSplashScreen(navController: NavController) {
    val defaultCity = "Seattle"
    val scale = remember {
      Animatable(0f)
    }

    LaunchedEffect(key1 = true){
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 800,
                delayMillis = 200,
                easing = {
                    OvershootInterpolator(8f).getInterpolation(it)
                }
            )
        )

        delay(2000L)
        navController.navigate(route = WeatherScreens.MainScreen.name+"/$defaultCity")
                               {
                                    popUpTo(WeatherScreens.SplashScreen.name){
                                        inclusive=true
                                    }
                                    launchSingleTop=true

                                    restoreState=true

                                }
    }
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .size(300.dp)
            .scale(scale.value),
            shape = CircleShape,
            color = Color.White,
//            border = BorderStroke(2.dp, Color.LightGray),
            shadowElevation = 5.dp,
            tonalElevation = 0.dp,
        ) {
    
        Column(modifier = Modifier.padding(1.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
            Image(painter = painterResource(id = R.drawable.clearsky),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(190.dp),)
            Text(text = "Find The Sun?",
                modifier=Modifier.padding(top = 4.dp),
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold)
            )
        }

    }
}