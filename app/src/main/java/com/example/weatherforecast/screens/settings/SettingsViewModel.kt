package com.example.weatherforecast.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.model.Unit
import com.example.weatherforecast.repository.weatherDBRepository.WeatherDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDBRepository):ViewModel() {

    private var _units = MutableStateFlow<List<Unit>>(emptyList())
    val units=_units

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnit().distinctUntilChanged().collect{unitList->
                if(!unitList.isNullOrEmpty()){
                    _units.value = unitList
                }
            }
        }
    }

    fun insertUnit(unit: Unit)=viewModelScope.launch {
        repository.insertUnit(unit=unit)
    }

    fun updateUnit(unit: Unit)=viewModelScope.launch {
        repository.updateUnit(unit = unit)
    }
//
//    fun deleteUnit(unit: Unit)=viewModelScope.launch {
//
//    }
//
}