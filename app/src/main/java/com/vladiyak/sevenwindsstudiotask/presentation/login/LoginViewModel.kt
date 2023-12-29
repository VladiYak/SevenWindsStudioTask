package com.vladiyak.sevenwindsstudiotask.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladiyak.sevenwindsstudiotask.data.models.AuthState
import com.vladiyak.sevenwindsstudiotask.data.models.signup.Token
import com.vladiyak.sevenwindsstudiotask.data.models.signup.User
import com.vladiyak.sevenwindsstudiotask.data.repository.AuthenticationRepository
import com.vladiyak.sevenwindsstudiotask.domain.MainRepository
import com.vladiyak.sevenwindsstudiotask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthenticationRepository
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            authRepository.state.first { it is AuthState.Authorized }

            _uiEvent.send(UiEvent.NavigateToNearbyCoffeeShops)
        }
    }

    fun logIn(email: String, password: String) {
        val validationErrorEvent = if (email.isBlank()) {
            UiEvent.ErrorEmptyEmail
        } else if (password.isBlank()) {
            UiEvent.ErrorEmptyPassword
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
                    is ConnectException -> UiEvent.ErrorConnection
                    is HttpException -> UiEvent.ErrorRequest
                    is RuntimeException -> UiEvent.ErrorInvalidCredentials
                    else -> {
                        exception.printStackTrace()
                        UiEvent.ErrorUnknown
                    }
                }

                _uiEvent.send(errorEvent)
            }
        }
    }
}

data class UiState(
    val isLoading: Boolean = false
)

sealed interface UiEvent {
    data object ErrorUnknown : UiEvent
    data object ErrorEmptyEmail : UiEvent
    data object ErrorEmptyPassword : UiEvent
    data object ErrorConnection : UiEvent
    data object ErrorRequest : UiEvent
    data object ErrorInvalidCredentials : UiEvent

    data object NavigateToNearbyCoffeeShops : UiEvent
}
