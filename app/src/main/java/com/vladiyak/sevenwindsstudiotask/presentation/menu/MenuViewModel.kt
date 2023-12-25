package com.vladiyak.sevenwindsstudiotask.presentation.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeList
import com.vladiyak.sevenwindsstudiotask.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    private val _coffeeItem = MutableLiveData<CoffeeList>()
    val coffeeItem: LiveData<CoffeeList> = _coffeeItem

    fun getCoffeeItem(id: String, token: String) {
        viewModelScope.launch {
            val coffeeItem = repository.getLocationMenu(id, token)
            _coffeeItem.postValue(coffeeItem)
        }

    }
}