package com.vladiyak.sevenwindsstudiotask.presentation.nearbycoffeeshops

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationItem
import com.vladiyak.sevenwindsstudiotask.data.repository.CoffeeShopsRepositoryImpl
import com.vladiyak.sevenwindsstudiotask.data.repository.GeoLocationRepository
import com.vladiyak.sevenwindsstudiotask.domain.usecase.GetCoffeeShopsUseCase
import com.vladiyak.sevenwindsstudiotask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NearbyCoffeeShopsViewModel @Inject constructor(
    private val repository: CoffeeShopsRepositoryImpl,
    private val geoLocationRepository: GeoLocationRepository,
    private val getGetCoffeeShopsUseCase: GetCoffeeShopsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        refreshAll()
        getCoffeeShops()
    }

    fun getCoffeeShops() {
        viewModelScope.launch {
            getGetCoffeeShopsUseCase().collect { coffeeShops ->
                when (coffeeShops) {
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                coffeeShops = coffeeShops.data ?: emptyList()
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Error -> {
                        _uiState.update { it.copy(message = coffeeShops.message ?: "Error!") }
                    }
                }
            }
        }
    }

    fun refreshAll() {
        setLoadingState(true)
        viewModelScope.launch {
            try {
                repository.refreshAll()
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(message = exception.message ?: "Unknown error")
                }
            }
        }
    }


    fun refreshCurrentLocation() {
        geoLocationRepository.refreshCurrentLocation(false)
    }

    private fun setLoadingState(isLoading: Boolean) {
        _uiState.update {
            it.copy(isLoading = isLoading)
        }
    }
}



