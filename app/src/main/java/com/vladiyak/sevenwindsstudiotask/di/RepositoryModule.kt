package com.vladiyak.sevenwindsstudiotask.di

import com.vladiyak.sevenwindsstudiotask.data.repository.AuthRepositoryImpl
import com.vladiyak.sevenwindsstudiotask.data.repository.CartRepositoryImpl
import com.vladiyak.sevenwindsstudiotask.data.repository.CoffeeShopsRepositoryImpl
import com.vladiyak.sevenwindsstudiotask.data.repository.GeoLocationRepositoryImpl
import com.vladiyak.sevenwindsstudiotask.data.repository.MenuRepositoryImpl
import com.vladiyak.sevenwindsstudiotask.domain.repository.AuthRepository
import com.vladiyak.sevenwindsstudiotask.domain.repository.CartRepository
import com.vladiyak.sevenwindsstudiotask.domain.repository.CoffeeShopsRepository
import com.vladiyak.sevenwindsstudiotask.domain.repository.GeoLocationRepository
import com.vladiyak.sevenwindsstudiotask.domain.repository.MenuRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMenuRepository(menuRepositoryImpl: MenuRepositoryImpl): MenuRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(cartRepositoryImpl: CartRepositoryImpl): CartRepository

    @Binds
    @Singleton
    abstract fun bindCoffeeShopsRepository(coffeeShopsRepositoryImpl: CoffeeShopsRepositoryImpl): CoffeeShopsRepository

    @Binds
    @Singleton
    abstract fun bindGeoLocationRepository(geoLocationRepositoryImpl: GeoLocationRepositoryImpl): GeoLocationRepository
}