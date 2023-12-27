package com.vladiyak.sevenwindsstudiotask.presentation.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeList
import com.vladiyak.sevenwindsstudiotask.data.repository.MainRepository
import com.vladiyak.sevenwindsstudiotask.utils.Resource
import com.vladiyak.sevenwindsstudiotask.utils.correctId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _coffeeItem = MutableLiveData<Resource<MutableList<CoffeeItem>>>()
    val coffeeItem: LiveData<Resource<MutableList<CoffeeItem>>> = _coffeeItem

    fun getCoffeeItem(id: String) {
        viewModelScope.launch {
            val coffeeItem = repository.getLocationMenu(id)
            if (coffeeItem.isSuccessful) {
                _coffeeItem.postValue(
                    Resource.Success(
                        coffeeItem.body()?.toMutableList() ?: mutableListOf()
                    )
                )
            } else {
                _coffeeItem.postValue(Resource.Error(coffeeItem.errorBody().toString()))
            }
        }
    }

    fun increaseQuantity(list: MutableList<CoffeeItem>, item: CoffeeItem) {
        list[correctId(item) - 1].quantity += 1
        _coffeeItem.postValue(Resource.Success(list))
    }

    fun decreaseQuantity(list: MutableList<CoffeeItem>, item: CoffeeItem) {
        list[correctId(item) - 1].quantity -= 1
        _coffeeItem.postValue(Resource.Success(list))
    }
}