package com.vladiyak.sevenwindsstudiotask.presentation.map

import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationItem

data class MapUiState(
    val coffeeShops: List<LocationItem> = emptyList(),
    val moveCameraTo: CameraMovePosition? = null
)
