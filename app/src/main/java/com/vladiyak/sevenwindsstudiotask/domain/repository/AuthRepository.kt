package com.vladiyak.sevenwindsstudiotask.domain.repository

import com.vladiyak.sevenwindsstudiotask.data.models.signup.AuthState
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {

    val state: StateFlow<AuthState>

    suspend fun signUp(email: String, password: String)
    suspend fun logIn(email: String, password: String)
}
