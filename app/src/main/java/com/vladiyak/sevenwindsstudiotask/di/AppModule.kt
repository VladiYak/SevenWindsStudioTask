package com.vladiyak.sevenwindsstudiotask.di

import com.vladiyak.sevenwindsstudiotask.data.local.AuthenticationManager
import com.vladiyak.sevenwindsstudiotask.data.models.signup.AuthState
import com.vladiyak.sevenwindsstudiotask.data.network.coffeeapi.CoffeeApiService
import com.vladiyak.sevenwindsstudiotask.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient): CoffeeApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authManager: AuthenticationManager): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val authState = authManager.state.value
                val request = if (authState is AuthState.Authorized) {
                    val newRequest = chain.request().newBuilder().apply {
                        addHeader("Cache-Control", "no-cache")
                        addHeader("Authorization", "Bearer ${authState.token}")
                    }
                    newRequest.build()
                } else {
                    chain.request()
                }
                return@addInterceptor chain.proceed(request)
            }
            .build()
    }
}