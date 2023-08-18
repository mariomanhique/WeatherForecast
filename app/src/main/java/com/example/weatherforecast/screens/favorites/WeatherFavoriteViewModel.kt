package com.example.weatherforecast.screens.favorites
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.model.Favorite
import com.example.weatherforecast.repository.weatherDBRepository.WeatherDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherFavoriteViewModel @Inject constructor(private val repository: WeatherDBRepository): ViewModel(){

    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
           repository.getFavorites().distinctUntilChanged().collect{list->
               if(list.isNotEmpty()){
                   _favList.value =  list
               }
            }

        }
    }

    fun insert(fav: Favorite) = viewModelScope.launch {
        repository.insert(fav)
    }

//    fun getFavoriteByCity(city:String) = viewModelScope.launch {
//        repository.getFavoriteByCity(city)
//    }

    fun delete(fav: Favorite) = viewModelScope.launch {
        repository.delete(fav)
    }


}