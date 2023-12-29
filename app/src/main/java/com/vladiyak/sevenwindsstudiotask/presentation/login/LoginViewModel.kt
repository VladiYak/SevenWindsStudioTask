package com.vladiyak.sevenwindsstudiotask.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladiyak.sevenwindsstudiotask.data.models.signup.AuthState
import com.vladiyak.sevenwindsstudiotask.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiEvent = Channel<LoginUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            authRepository.state.first { it is AuthState.Authorized }

            _uiEvent.send(LoginUiEvent.NavigateToNearbyCoffeeShops)
        }
    }

    fun logIn(email: String, password: String) {
        val validationErrorEvent = if (email.isBlank()) {
            LoginUiEvent.ErrorEmptyEmail
        } else if (password.isBlank()) {
            LoginUiEvent.ErrorEmptyPassword
        } else null

        if (validationErrorEvent != null) {
            viewModelScope.launch {
                _uiEvent.send(validationErrorEvent)
            }
            return
        }

        viewModelScope.launch {
            try {
                authRepository.logIn(email, password)
            } catch (exception: Exception) {
                val errorEvent = when (exception) {
                    is ConnectException -> LoginUiEvent.ErrorConnection
                    is HttpException -> LoginUiEvent.ErrorRequest
                    is RuntimeException -> LoginUiEvent.ErrorInvalidCredentials
                    else -> {
                        exception.printStackTrace()
                        LoginUiEvent.ErrorUnknown
                    }
                }

                _uiEvent.send(errorEvent)
            }
        }
    }
}


