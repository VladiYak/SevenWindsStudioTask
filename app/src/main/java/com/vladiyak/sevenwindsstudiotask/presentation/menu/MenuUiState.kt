package com.vladiyak.sevenwindsstudiotask.presentation.menu

import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem

data class MenuUiState(
    val isLoading: Boolean = false,
    val canProceed: Boolean = false,
    val menuItems: List<CoffeeItem> = emptyList(),
)
