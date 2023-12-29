package com.vladiyak.sevenwindsstudiotask.data.local

import com.vladiyak.sevenwindsstudiotask.data.models.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationManager @Inject constructor() {

    private val _state: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.Unauthorized)
    val state = _state.asStateFlow()

    fun authorize(token: String) {
        _state.update {
            AuthState.Authorized(token)
        }
    }

    fun logout() {
        _state.value = AuthState.Unauthorized
    }

}