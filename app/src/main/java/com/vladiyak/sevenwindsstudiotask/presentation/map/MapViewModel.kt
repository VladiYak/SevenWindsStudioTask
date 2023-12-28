package com.vladiyak.sevenwindsstudiotask.presentation.map

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationList
import com.vladiyak.sevenwindsstudiotask.domain.MainRepository
import com.vladiyak.sevenwindsstudiotask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    private val _locations = MutableLiveData<Resource<LocationList>>()
    val locations: LiveData<Resource<LocationList>> = _locations


    @SuppressLint("SuspiciousIndentation")
    fun getLocations() {
        viewModelScope.launch {
            val coffeeShops = repository.getLocations()
            if (coffeeShops.isSuccessful) {
                _locations.postValue(coffeeShops.body()?.let { Resource.Success(it) })
            } else {
                _locations.postValue(Resource.Error(coffeeShops.errorBody().toString()))
            }
        }

    }
}