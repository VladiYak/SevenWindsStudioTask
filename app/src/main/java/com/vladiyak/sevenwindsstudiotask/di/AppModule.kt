package com.vladiyak.sevenwindsstudiotask.di

import com.vladiyak.sevenwindsstudiotask.data.network.coffeeapi.CoffeeApiService
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

object AppModule {

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient): CoffeeApiService {
        return Retrofit.Builder()
            .baseUrl("http://147.78.66.203:3210/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
//            .addInterceptor { apiKeyAsQuery(it) }
            .build()
    }
}