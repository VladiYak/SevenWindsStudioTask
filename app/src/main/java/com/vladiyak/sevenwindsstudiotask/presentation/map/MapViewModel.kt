package com.vladiyak.sevenwindsstudiotask.presentation.map

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationList
import com.vladiyak.sevenwindsstudiotask.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    private val _locations = MutableLiveData<LocationList>()
    val locations: LiveData<LocationList> = _locations


    @SuppressLint("SuspiciousIndentation")
    fun getLocations() {
        viewModelScope.launch {
            val coffeeShops = repository.getLocations()
            _locations.postValue(coffeeShops)
        }

    }
}