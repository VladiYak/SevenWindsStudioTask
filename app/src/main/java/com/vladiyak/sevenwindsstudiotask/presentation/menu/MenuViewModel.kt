package com.vladiyak.sevenwindsstudiotask.presentation.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeList
import com.vladiyak.sevenwindsstudiotask.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _coffeeItem = MutableLiveData<MutableList<CoffeeItem>>()
    val coffeeItem: LiveData<MutableList<CoffeeItem>> = _coffeeItem

    fun getCoffeeItem(id: String, token: String) {
        viewModelScope.launch {
            val coffeeItem = repository.getLocationMenu(id, token)
            _coffeeItem.postValue(coffeeItem)
        }
    }

    fun increaseQuantity(list: MutableList<CoffeeItem>, item: CoffeeItem) {
        list[correctId(item) - 1].quantity += 1
        _coffeeItem.postValue(list)
    }

    fun decreaseQuantity(list: MutableList<CoffeeItem>, item: CoffeeItem) {
        list[correctId(item) - 1].quantity -= 1
        _coffeeItem.postValue(list)
    }

    private fun correctId(item: CoffeeItem): Int {
        if (item.id in 5..8) {
            return item.id - 4
        } else if (
            item.id in 9..12
        ) {
            return item.id - 8
        }
        return item.id
    }
}