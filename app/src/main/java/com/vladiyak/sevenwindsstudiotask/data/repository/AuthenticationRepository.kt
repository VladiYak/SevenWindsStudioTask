package com.vladiyak.sevenwindsstudiotask.data.repository

import com.vladiyak.sevenwindsstudiotask.data.local.AuthenticationManager
import com.vladiyak.sevenwindsstudiotask.data.models.AuthState
import com.vladiyak.sevenwindsstudiotask.data.models.signup.User
import com.vladiyak.sevenwindsstudiotask.data.network.coffeeapi.CoffeeApiService
import kotlinx.coroutines.flow.StateFlow
import retrofit2.HttpException
import java.net.HttpURLConnection
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val authManager: AuthenticationManager,
    private val service: CoffeeApiService
) {

    val state: StateFlow<AuthState>
        get() = authManager.state

    suspend fun signUp(email: String, password: String) {
        val response = service.signUp(User(email, password))
        if (!response.isSuccessful) {
            if (response.code() == HttpURLConnection.HTTP_NOT_ACCEPTABLE) {
                throw RuntimeException()
            } else {
                throw HttpException(response)
            }
        }
        val body = response.body() ?: throw RuntimeException()
        authManager.authorize(body.token)
    }

   suspend fun logIn(email: String, password: String) {
        val response = service.login(User(email, password))
        if (!response.isSuccessful) {
            if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                throw RuntimeException()
            } else {
                throw HttpException(response)
            }
        }
        val body = response.body() ?: throw RuntimeException()
        authManager.authorize(body.token)
    }
}
