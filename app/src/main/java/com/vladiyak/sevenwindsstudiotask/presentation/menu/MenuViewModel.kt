package com.vladiyak.sevenwindsstudiotask.presentation.menu

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladiyak.sevenwindsstudiotask.data.repository.CartRepositoryImpl
import com.vladiyak.sevenwindsstudiotask.domain.repository.MenuRepository
import com.vladiyak.sevenwindsstudiotask.domain.usecase.GetCartMenuItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val menuRepository: MenuRepository,
    private val cartRepository: CartRepositoryImpl,
    private val getCartMenuItemsStream: GetCartMenuItemsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args = MenuFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _uiState = MutableStateFlow(MenuUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<MenuUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        refresh()
        viewModelScope.launch {
            cartRepository.deleteAll()

            getCartMenuItemsStream(args.id).collectLatest { cartMenuItems ->
                val canProceed = cartMenuItems.any { it.quantity > 0 }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        canProceed = canProceed,
                        menuItems = cartMenuItems
                    )
                }
            }
        }
    }

    fun refresh() {
        setLoadingState(true)

        viewModelScope.launch {
            try {
                menuRepository.refreshById(args.id)
            } catch (exception: Exception) {
                val errorEvent = when (exception) {
                    is ConnectException -> MenuUiEvent.ErrorConnection
                    else -> MenuUiEvent.ErrorLoading
                }
                _uiEvent.send(errorEvent)
                setLoadingState(false)
            }
        }
    }

    fun addToCart(menuItemId: Int) {
        viewModelScope.launch {
            cartRepository.add(menuItemId)
        }
    }

    fun removeFromCart(menuItemId: Int) {
        viewModelScope.launch {
            cartRepository.remove(menuItemId)
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        _uiState.update {
            it.copy(isLoading = isLoading)
        }
    }
}



