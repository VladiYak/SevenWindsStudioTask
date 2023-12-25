package com.vladiyak.sevenwindsstudiotask.data.network.coffeeapi

import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationItem
import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationList
import com.vladiyak.sevenwindsstudiotask.data.models.signup.Token
import com.vladiyak.sevenwindsstudiotask.data.models.signup.User
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CoffeeApiService {

    @GET("locations")
    suspend fun getLocationsList(): LocationList

    @GET("location/{id}/menu")
    suspend fun getLocation(
        @Path("id") id: String
    ): LocationItem


    @POST("auth/register")
    suspend fun signUp(
        @Body user: User
    ): Token

    @POST("auth/login")
    suspend fun login(
        @Body user: User
    ): Token
}