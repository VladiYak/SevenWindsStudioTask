package com.vladiyak.sevenwindsstudiotask.data.network.coffeeapi

import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationItem
import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationList
import com.vladiyak.sevenwindsstudiotask.data.models.signup.Token
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CoffeeApiService {

    @GET("locations")
    suspend fun getLocationsList(): LocationList

    @GET("location/{id}/menu")
    suspend fun getLocation(
        @Path("id") id: String
    ): LocationItem

    @POST("auth/register")
    suspend fun signUp(
        @Field("login") login: String,
        @Field("password") password: String
    ): Token

    @POST("auth/login")
    suspend fun login(
        @Field("login") login: String,
        @Field("password") password: String
    ): Token
}