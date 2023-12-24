package com.vladiyak.sevenwindsstudiotask.data.repository

import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationItem
import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationList
import com.vladiyak.sevenwindsstudiotask.data.models.signup.Token
import com.vladiyak.sevenwindsstudiotask.data.network.coffeeapi.CoffeeApiService
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: CoffeeApiService
) {

    suspend fun getLocations(): LocationList {
        return apiService.getLocationsList()
    }

    suspend fun getLocation(id: String): LocationItem {
        return apiService.getLocation(id)
    }

    suspend fun signUp(login: String, password: String): Token {
        return apiService.signUp(login, password)
    }

    suspend fun login(login: String, password: String): Token {
        return apiService.login(login, password)
    }

}