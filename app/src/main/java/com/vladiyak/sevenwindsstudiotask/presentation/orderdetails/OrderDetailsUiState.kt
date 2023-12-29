package com.vladiyak.sevenwindsstudiotask.presentation.orderdetails

import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem

data class OrderDetailsUiState(
    val isLoading: Boolean = false,
    val canProceed: Boolean = false,
    val isEmpty: Boolean = false,
    val menuItems: List<CoffeeItem> = emptyList(),
    val totalPrice: Int = 0
)