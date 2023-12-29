package com.vladiyak.sevenwindsstudiotask.presentation.orderdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladiyak.sevenwindsstudiotask.data.repository.CartRepositoryImpl
import com.vladiyak.sevenwindsstudiotask.domain.usecase.GetCartMenuItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val cartRepository: CartRepositoryImpl,
    private val getCartMenuItemsStream: GetCartMenuItemsUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(OrderDetailsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            setLoadingState(true)

            getCartMenuItemsStream().collectLatest { cartMenuItems ->
                val canProceed = cartMenuItems.any { it.quantity > 0 }
                val totalPrice = cartMenuItems.fold(0) { acc, cartMenuItem ->
                    acc + cartMenuItem.price * cartMenuItem.quantity
                }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        canProceed = canProceed,
                        isEmpty = cartMenuItems.isEmpty(),
                        menuItems = cartMenuItems,
                        totalPrice = totalPrice
                    )
                }
            }

            setLoadingState(false)
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

    fun clearCart() {
        viewModelScope.launch {
            cartRepository.deleteAll()
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        _uiState.update {
            it.copy(isLoading = isLoading)
        }
    }
}

