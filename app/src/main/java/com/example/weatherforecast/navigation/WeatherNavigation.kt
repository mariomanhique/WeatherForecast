package com.example.weatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherforecast.screens.main.MainScreen
import com.example.weatherforecast.screens.main.WeatherViewModel
import com.example.weatherforecast.screens.nextDaysFr.NextDaysForecastScreen
import com.example.weatherforecast.screens.search.SearchScreen
import com.example.weatherforecast.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation(weatherViewModel: WeatherViewModel = hiltViewModel()){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name){

        composable(WeatherScreens.SplashScreen.name){
            WeatherSplashScreen(navController=navController)
        }

        composable(WeatherScreens.MainScreen.name){
            MainScreen(navController = navController,weatherViewModel)
        }

        composable(WeatherScreens.AboutScreen.name){

        }

        composable(WeatherScreens.Next10daysScreen.name + "/{city}", arguments = listOf(navArgument(name = "city"){
            type= NavType.StringType
        })){

            NextDaysForecastScreen(
                navController=navController,
                it.arguments?.getString("city"),
                weatherViewModel=weatherViewModel)
        }

        composable(WeatherScreens.FavoriteScreen.name){

        }

        composable(WeatherScreens.SearchScreen.name){
            SearchScreen()
        }

        composable(WeatherScreens.SettingsScreen.name){

        }
    }
}