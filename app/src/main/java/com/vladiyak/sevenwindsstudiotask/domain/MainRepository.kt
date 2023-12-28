package com.vladiyak.sevenwindsstudiotask.domain

import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationList
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeList
import com.vladiyak.sevenwindsstudiotask.data.models.signup.Token
import com.vladiyak.sevenwindsstudiotask.data.models.signup.User
import retrofit2.Response

interface MainRepository {

    suspend fun getLocations(): Response<LocationList>

    suspend fun getLocationMenu(id: String): Response<CoffeeList>

    suspend fun signUp(user: User): Response<Token>

    suspend fun login(user: User): Response<Token>
}