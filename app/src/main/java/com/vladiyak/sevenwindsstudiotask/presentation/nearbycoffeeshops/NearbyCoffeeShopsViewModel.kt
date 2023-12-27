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
import com.vladiyak.sevenwindsstudiotask.utils.Resource
import com.vladiyak.sevenwindsstudiotask.utils.correctId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NearbyCoffeeShopsViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    private val _coffeeShop = MutableLiveData<Resource<MutableList<LocationItem>>>()
    val coffeeShop: LiveData<Resource<MutableList<LocationItem>>> = _coffeeShop


    @SuppressLint("SuspiciousIndentation")
    fun getCoffeeShops() {
        viewModelScope.launch {
            _coffeeShop.postValue(Resource.Loading())
            val coffeeShops = repository.getLocations()
            if (coffeeShops.isSuccessful) {
                _coffeeShop.postValue(coffeeShops.body()?.let { Resource.Success(it.toMutableList()) })
            } else {
                _coffeeShop.postValue(Resource.Error(coffeeShops.errorBody().toString()))
            }
        }
    }

    fun setDistance(list: MutableList<LocationItem>, id: Int, distance: Double) {
        list[id - 1].distance = distance
        _coffeeShop.postValue(Resource.Success(list))
    }


}

