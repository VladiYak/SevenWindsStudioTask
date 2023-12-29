package com.vladiyak.sevenwindsstudiotask.presentation.nearbycoffeeshops

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladiyak.sevenwindsstudiotask.domain.repository.CoffeeShopsRepository
import com.vladiyak.sevenwindsstudiotask.domain.repository.GeoLocationRepository
import com.vladiyak.sevenwindsstudiotask.domain.usecase.GetCoffeeShopsUseCase
import com.vladiyak.sevenwindsstudiotask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NearbyCoffeeShopsViewModel @Inject constructor(
    private val repository: CoffeeShopsRepository,
    private val geoLocationRepository: GeoLocationRepository,
    private val getGetCoffeeShopsUseCase: GetCoffeeShopsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NearbyCoffeeShopsUiState())
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



