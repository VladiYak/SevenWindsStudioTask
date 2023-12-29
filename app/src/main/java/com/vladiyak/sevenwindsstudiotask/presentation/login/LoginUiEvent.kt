package com.vladiyak.sevenwindsstudiotask.presentation.login

sealed interface LoginUiEvent {
    data object ErrorUnknown : LoginUiEvent
    data object ErrorEmptyEmail : LoginUiEvent
    data object ErrorEmptyPassword : LoginUiEvent
    data object ErrorConnection : LoginUiEvent
    data object ErrorRequest : LoginUiEvent
    data object ErrorInvalidCredentials : LoginUiEvent

    data object NavigateToNearbyCoffeeShops : LoginUiEvent
}