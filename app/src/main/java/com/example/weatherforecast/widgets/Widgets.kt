package com.example.weatherforecast.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.weatherforecast.R
import com.example.weatherforecast.model.Favorite
import com.example.weatherforecast.navigation.WeatherScreens
import com.example.weatherforecast.screens.favorites.WeatherFavoriteViewModel
import com.example.weatherforecast.ui.theme.TxtColor
import com.example.weatherforecast.utils.fontFamily

@Composable
fun WeatherAppBar(title:String,
                  timestamp:String,
                  icon: ImageVector?,
                  favState: MutableState<Boolean> =
                      mutableStateOf(false),
                  isMainScreenOrRelated:Boolean=true,
                  onBackClicked:() -> Unit,
                  onFavClicked:(Boolean)->Unit={},
                  onSearchClick:()-> Unit,
                  onMenuClick:()-> Unit){

    //Country/City and time, year and week day
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            if(icon != null){
                Card(modifier = Modifier,
                    colors = CardDefaults.cardColors(containerColor = Color(0xff202328))) {
                    Image(imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(30.dp)
                            .clickable {
                                onBackClicked.invoke()
                            },alpha=0.7f, colorFilter = ColorFilter.tint(Color.LightGray))
                }

            }

            if(isMainScreenOrRelated){

                Icon(imageVector = Icons.Default.Favorite,
                    contentDescription = "",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            favState.value = !favState.value
                            onFavClicked(favState.value)
                        },
                     tint = if (!favState.value){
                         Color.White
                     } else {
                         Color.Red
                     }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(0.dp)
            ) {

                Text(text = title,
                    color = TxtColor,
                    style = TextStyle(
                    fontSize = 20.sp,
                    letterSpacing = 1.sp,
                    fontFamily = fontFamily("Russo One",FontWeight.Bold, FontStyle.Normal)
                    )
                )
                if (isMainScreenOrRelated){
                    Text(text = timestamp,
                        color = Color.Gray,
                        style=TextStyle(
                            fontSize = 15.sp,
                            fontFamily = fontFamily("Chakra Petch", FontWeight.SemiBold, FontStyle.Normal)
                        )
                    )
                }

            }

            if(isMainScreenOrRelated){
                    AsyncImage(
                        model = R.drawable.search,
                        contentDescription = "",
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                onSearchClick.invoke()
                            },
                        alpha = 0.6f)

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
fun ShowSettingDialog(showDialog: MutableState<Boolean>, navController: NavController) {

        var expanded by remember {
            mutableStateOf(true)
        }

        val items = listOf("Favorites","Settings","About")

        Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)) {
            
            DropdownMenu(expanded = expanded,
                onDismissRequest = {
                    expanded = false
                    showDialog.value = false
                }, modifier = Modifier
                    .width(140.dp)
                    .background(Color.White)) {

                items.forEachIndexed{ _, text->
                    DropdownMenuItem(text = {
                       Row(horizontalArrangement = Arrangement.Center,
                       verticalAlignment = Alignment.CenterVertically){
                            Icon(
                                imageVector =
                                when (text) {
                                    "Favorites" -> Icons.Default.Favorite
                                    "Settings" -> Icons.Default.Settings
                                    else -> Icons.Default.Info

                                }, contentDescription = "",
                                tint = Color.LightGray
                            )
                            Text(text = text, modifier = Modifier
                                .padding(start = 2.dp)
                                .clickable {
                                    navController.navigate(
                                        when (text) {
                                            "Favorites" -> WeatherScreens.FavoriteScreen.name
                                            "Settings" -> WeatherScreens.SettingsScreen.name
                                            else -> WeatherScreens.AboutScreen.name
                                        }
                                    )

                                })
                        }
                    }, onClick = {
                           expanded= false
                           showDialog.value = false
                        })
                }
            }
        }
}

@Composable
fun TextButtonsFilters(onTextClicked:(String)->Unit){
    val list = listOf("Today","Tomorrow","Next 10 Days")

    var buttonState by remember {
        mutableStateOf(0)
    }

    val updateText: (Int) -> Unit= remember {
        //This lambda allow us to have an integer to control our index to check the correct choice

        {
            buttonState=it
        }
    }

    Row {
        list.forEachIndexed { index, text ->

            Column {
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

@Composable
fun FavoriteRow(fav: Favorite,
                favoriteViewModel: WeatherFavoriteViewModel){
    Card(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth()
        .height(50.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xff202328))
    ) {
        Row(modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = fav.city,
                modifier = Modifier.width(100.dp),
                style = TextStyle(
                    fontFamily = fontFamily("Chakra Petch", FontWeight.SemiBold, FontStyle.Normal),
                    fontSize = 19.sp,
                    color = TxtColor
                )
            )
            Text(text = fav.country,
            style = TextStyle(
                fontFamily = fontFamily("Chakra Petch", FontWeight.SemiBold, FontStyle.Normal),
                fontSize = 15.sp,
                color = Color.Gray
               )
            )
            Icon(imageVector = Icons.Default.Delete,
                contentDescription = "Delete Icon",
                modifier = Modifier.clickable {
                    favoriteViewModel.delete(fav=fav)
                }.scale(1.2f), tint = TxtColor
            )
        }
    }
}