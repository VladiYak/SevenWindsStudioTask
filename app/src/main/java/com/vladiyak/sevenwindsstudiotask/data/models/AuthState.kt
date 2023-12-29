package com.vladiyak.sevenwindsstudiotask.data.models

sealed interface AuthState {
    data object Unauthorized : AuthState
    data class Authorized(val token: String) : AuthState
}