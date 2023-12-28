package com.vladiyak.sevenwindsstudiotask.data.repository

import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationList
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeList
import com.vladiyak.sevenwindsstudiotask.data.models.signup.Token
import com.vladiyak.sevenwindsstudiotask.data.models.signup.User
import com.vladiyak.sevenwindsstudiotask.data.network.coffeeapi.CoffeeApiService
import com.vladiyak.sevenwindsstudiotask.domain.MainRepository
import retrofit2.Response
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val apiService: CoffeeApiService
): MainRepository {

    override suspend fun getLocations(): Response<LocationList> {
        return apiService.getLocationsList()
    }

    override suspend fun getLocationMenu(id: String): Response<CoffeeList> {
        return apiService.getLocationMenu(id)
    }

    override suspend fun signUp(user: User): Response<Token> {
        return apiService.signUp(user)
    }

    override suspend fun login(user: User): Response<Token> {
        return apiService.login(user)
    }

}