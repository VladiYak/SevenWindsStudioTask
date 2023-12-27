package com.vladiyak.sevenwindsstudiotask.presentation.nearbycoffeeshops

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationItem
import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationList
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem
import com.vladiyak.sevenwindsstudiotask.data.models.signup.Token
import com.vladiyak.sevenwindsstudiotask.data.repository.MainRepository
import com.vladiyak.sevenwindsstudiotask.utils.correctId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NearbyCoffeeShopsViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    private val _coffeeShop = MutableLiveData<MutableList<LocationItem>>()
    val coffeeShop: LiveData<MutableList<LocationItem>> = _coffeeShop


    @SuppressLint("SuspiciousIndentation")
    fun getCoffeeShops() {
        viewModelScope.launch {
            val coffeeShops = repository.getLocations()
                _coffeeShop.postValue(coffeeShops)
        }

    }

    fun setDistance(list: MutableList<LocationItem>, id: Int, distance: Double) {
        list[id - 1].distance = distance
        _coffeeShop.postValue(list)
    }


}