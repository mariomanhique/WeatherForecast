package com.example.weatherforecast.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherforecast.model.Favorite
import com.example.weatherforecast.ui.theme.BgColor
import com.example.weatherforecast.widgets.FavoriteRow
import com.example.weatherforecast.widgets.WeatherAppBar



@Composable
fun FavoritesScreen(navController:NavController,favoriteViewModel: WeatherFavoriteViewModel) {



MainScaffold(navController = navController,favoriteViewModel)

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(navController: NavController,
                 favoriteViewModel: WeatherFavoriteViewModel){
    val favList:List<Favorite> = favoriteViewModel.favList.collectAsState().value
    Scaffold(topBar = {
        WeatherAppBar(
            title = "Favorites",
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
            verticalArrangement = Arrangement.Top
        ) {

            MainContent(favList, favoriteViewModel =favoriteViewModel)
        }
    }
}

@Composable
fun MainContent(favList:List<Favorite>,
                favoriteViewModel: WeatherFavoriteViewModel){
    LazyColumn(){
        items(favList){favItem->

            FavoriteRow(favItem, favoriteViewModel = favoriteViewModel)

        }
    }
}