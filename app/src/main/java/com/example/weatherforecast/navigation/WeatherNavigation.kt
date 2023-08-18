package com.example.weatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherforecast.screens.about.AboutScreen
import com.example.weatherforecast.screens.favorites.FavoritesScreen
import com.example.weatherforecast.screens.favorites.WeatherFavoriteViewModel
import com.example.weatherforecast.screens.main.MainScreen
import com.example.weatherforecast.screens.main.WeatherViewModel
import com.example.weatherforecast.screens.nextDaysFr.NextDaysForecastScreen
import com.example.weatherforecast.screens.search.SearchScreen
import com.example.weatherforecast.screens.settings.SettingsScreen
import com.example.weatherforecast.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation(weatherViewModel: WeatherViewModel = hiltViewModel(),
                        favoriteViewModel: WeatherFavoriteViewModel= hiltViewModel()){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name){

        composable(WeatherScreens.SplashScreen.name){
            WeatherSplashScreen(navController=navController)
        }

        val route = WeatherScreens.MainScreen.name
        composable("$route/{city}", arguments = listOf(navArgument(name = "city"){
            type = NavType.StringType
        })){navBack->
            navBack.arguments?.getString("city").let {city->

                MainScreen(navController = navController,city,weatherViewModel,favoriteViewModel)

            }


        }

        composable(WeatherScreens.AboutScreen.name){
            AboutScreen(navController=navController)
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
            FavoritesScreen(navController=navController, favoriteViewModel = favoriteViewModel)
        }

        composable(WeatherScreens.SearchScreen.name){
            SearchScreen(navController = navController)
        }

        composable(WeatherScreens.SettingsScreen.name){
            SettingsScreen(navController)
        }
    }
}