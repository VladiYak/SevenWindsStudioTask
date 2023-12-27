package com.vladiyak.sevenwindsstudiotask.data.repository

import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationList
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeList
import com.vladiyak.sevenwindsstudiotask.data.models.signup.Token
import com.vladiyak.sevenwindsstudiotask.data.models.signup.User
import com.vladiyak.sevenwindsstudiotask.data.network.coffeeapi.CoffeeApiService
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: CoffeeApiService
) {

    suspend fun getLocations(): LocationList {
        return apiService.getLocationsList()
    }

    suspend fun getLocationMenu(id: String): CoffeeList {
        return apiService.getLocationMenu(id)
    }

    suspend fun signUp(user: User): Response<Token> {
        return apiService.signUp(user)
    }

    suspend fun login(user: User): Response<Token> {
        return apiService.login(user)
    }

}