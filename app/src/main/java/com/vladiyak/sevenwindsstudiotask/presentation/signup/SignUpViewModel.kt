package com.vladiyak.sevenwindsstudiotask.presentation.signup

import android.util.Patterns
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
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiEvent = Channel<SignUpUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            authRepository.state.first { it is AuthState.Authorized }

            _uiEvent.send(SignUpUiEvent.NavigateToNearbyCoffeeShops)
        }
    }

    fun signUp(email: String, password: String, passwordConfirmation: String) {
        val validationErrorEvent = if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            SignUpUiEvent.ErrorInvalidEmail
        } else if (password.isBlank()) {
            SignUpUiEvent.ErrorEmptyPassword
        } else if (password != passwordConfirmation) {
            SignUpUiEvent.ErrorPasswordsDoNotMatch
        } else null

        if (validationErrorEvent != null) {
            viewModelScope.launch {
                _uiEvent.send(validationErrorEvent)
            }
            return
        }

        viewModelScope.launch {
            try {
                val token = authRepository.signUp(email, password)
            } catch (exception: Exception) {
                val errorEvent = when (exception) {
                    is ConnectException -> SignUpUiEvent.ErrorConnection
                    is HttpException -> SignUpUiEvent.ErrorRequest
                    is RuntimeException -> SignUpUiEvent.ErrorAccountIsTaken
                    else -> {
                        exception.printStackTrace()
                        SignUpUiEvent.ErrorUnknown
                    }
                }

                _uiEvent.send(errorEvent)
            }
        }
    }
}

