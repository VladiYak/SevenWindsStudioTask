package com.vladiyak.sevenwindsstudiotask.data.models.signup

sealed interface AuthState {
    data object Unauthorized : AuthState
    data class Authorized(val token: String) : AuthState
}