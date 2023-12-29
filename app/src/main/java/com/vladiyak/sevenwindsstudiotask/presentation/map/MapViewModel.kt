package com.vladiyak.sevenwindsstudiotask.presentation.map

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationItem
import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationList
import com.vladiyak.sevenwindsstudiotask.data.repository.CoffeeShopsRepositoryImpl
import com.vladiyak.sevenwindsstudiotask.domain.MainRepository
import com.vladiyak.sevenwindsstudiotask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: CoffeeShopsRepositoryImpl,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args = MapFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private var savedCameraPosition: CameraMovePosition? = null

    init {
        if (args.id != 0) {
            viewModelScope.launch {
                repository.getCoffeeShopById(args.id)?.let { coffeeShop ->
                    _uiState.update {
                        it.copy(
                            moveCameraTo = CameraMovePosition(
                                coffeeShop.point.latitude, coffeeShop.point.longitude
                            )
                        )
                    }
                }
            }
        }

        viewModelScope.launch {
            repository.getCoffeeShopsStream().collect { coffeeShops ->
                _uiState.update {
                    it.copy(coffeeShops = coffeeShops)
                }
            }
        }
    }

    fun onCameraMoved() {
        _uiState.update {
            it.copy(moveCameraTo = null)
        }
    }

    fun restoreCameraPosition() {
        _uiState.update {
            it.copy(moveCameraTo = savedCameraPosition)
        }
        savedCameraPosition = null
    }

    fun saveCameraPosition(cameraPosition: CameraMovePosition) {
        savedCameraPosition = cameraPosition
    }
}

data class UiState(
    val coffeeShops: List<LocationItem> = emptyList(),
    val moveCameraTo: CameraMovePosition? = null
)

data class CameraMovePosition(
    val latitude: Double,
    val longitude: Double,
    val zoom: Float? = null,
    val azimuth: Float? = null,
    val tilt: Float? = null
)