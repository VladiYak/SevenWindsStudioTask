package com.vladiyak.sevenwindsstudiotask.presentation.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladiyak.sevenwindsstudiotask.data.models.signup.Token
import com.vladiyak.sevenwindsstudiotask.data.models.signup.User
import com.vladiyak.sevenwindsstudiotask.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    private val _token = MutableLiveData<Token>()
    val token: LiveData<Token> = _token

    fun login(user: User) {
        viewModelScope.launch {
            val token = repository.login(user)
            _token.postValue(token)
            Log.d("Token", "token $token")
        }
    }
}