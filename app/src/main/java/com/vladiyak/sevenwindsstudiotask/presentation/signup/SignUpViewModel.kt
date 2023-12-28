package com.vladiyak.sevenwindsstudiotask.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vladiyak.sevenwindsstudiotask.data.models.signup.Token
import com.vladiyak.sevenwindsstudiotask.data.models.signup.User
import com.vladiyak.sevenwindsstudiotask.domain.MainRepository
import com.vladiyak.sevenwindsstudiotask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _token = MutableLiveData<Resource<Token?>>()
    val token: LiveData<Resource<Token?>> = _token

    fun signUp(user: User) {
        viewModelScope.launch {
            val response = repository.signUp(user)
            if (response.isSuccessful) {
                _token.postValue(Resource.Success(response.body()))
            } else {
                _token.postValue(Resource.Error(response.errorBody().toString()))
            }
        }
    }
}