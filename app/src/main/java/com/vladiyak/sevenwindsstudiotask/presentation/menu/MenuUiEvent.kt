package com.vladiyak.sevenwindsstudiotask.presentation.menu

sealed interface MenuUiEvent {
    data object ErrorConnection : MenuUiEvent
    data object ErrorLoading : MenuUiEvent
}