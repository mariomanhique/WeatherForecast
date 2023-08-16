package com.example.weatherforecast.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherforecast.ui.theme.BgColor
import com.example.weatherforecast.widgets.WeatherAppBar

@Composable
fun SearchScreen(navController: NavController){

MainScaffold(navController=navController)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(navController: NavController){
    Scaffold(
        topBar = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                WeatherAppBar(
                    title = "Search",
                    isSearchScreen = true,
                    timestamp = "",
                    icon = Icons.Default.ArrowBack,
                    onNavigationClick = {
                        navController.popBackStack()
                    },
                    onSearchClick = { /*TODO*/ }) {

                }
            }

        },
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        containerColor = BgColor
    ) {paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {
            
            MainContent()

        }

    }
}

@Composable
fun MainContent(){
    Column(verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally) {

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTextField(valueState: MutableState<String>,
                    placeholder: String,
                    keyboardType: KeyboardType=KeyboardType.Text,
                    imeAction: ImeAction = ImeAction.Next,
                    onAction: KeyboardActions = KeyboardActions.Default) {
    
    OutlinedTextField(value = valueState.value, onValueChange = {
        valueState.value=it
    },
    label = {
        Text(text = placeholder)
    },
    maxLines = 1,
    singleLine = true,
    keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
    keyboardActions = onAction,
    colors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Color.Blue,
        cursorColor = Color.Black
    ), shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    )


}

@Composable
fun SearchBar(onSearch:()->Unit={}){
    //When we are dealing with forms, its good to use remember saveable
//    val searchQueryState = rememberSaveable(inputs = , stateSaver = ) {
//
//    }
//    Column() {
//        CommonTextField(
//            valueState = searchQueryState,
//            placeholder= "Seattle",
//            onAction = KeyboardActions {  }
//        )
//    }
}