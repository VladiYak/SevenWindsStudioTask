package com.vladiyak.sevenwindsstudiotask.presentation.nearbycoffeeshops

import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationItem

data class NearbyCoffeeShopsUiState(
    val isLoading: Boolean = false,
    val coffeeShops: List<LocationItem> = emptyList(),
    val message: String = ""
)
