package com.vladiyak.sevenwindsstudiotask.presentation.signup

sealed interface SignUpUiEvent {
    data object ErrorUnknown : SignUpUiEvent
    data object ErrorInvalidEmail : SignUpUiEvent
    data object ErrorEmptyPassword : SignUpUiEvent
    data object ErrorPasswordsDoNotMatch : SignUpUiEvent
    data object ErrorConnection : SignUpUiEvent
    data object ErrorRequest : SignUpUiEvent
    data object ErrorAccountIsTaken : SignUpUiEvent

    data object NavigateToNearbyCoffeeShops : SignUpUiEvent
}