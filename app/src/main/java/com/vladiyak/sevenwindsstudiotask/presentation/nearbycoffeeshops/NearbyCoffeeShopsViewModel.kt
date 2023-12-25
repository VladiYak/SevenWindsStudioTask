package com.vladiyak.sevenwindsstudiotask.presentation.nearbycoffeeshops

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationList
import com.vladiyak.sevenwindsstudiotask.data.models.signup.Token
import com.vladiyak.sevenwindsstudiotask.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NearbyCoffeeShopsViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    private val _coffeeShop = MutableLiveData<LocationList>()
    val coffeeShop: LiveData<LocationList> = _coffeeShop




    @SuppressLint("SuspiciousIndentation")
    fun getCoffeeShops(token: String) {
        viewModelScope.launch {
            val coffeeShops = repository.getLocations(token)
                _coffeeShop.postValue(coffeeShops)
        }

    }


}