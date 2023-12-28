package com.vladiyak.sevenwindsstudiotask.di

import com.vladiyak.sevenwindsstudiotask.data.repository.MainRepositoryImpl
import com.vladiyak.sevenwindsstudiotask.domain.MainRepository
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
    abstract fun bindCoinRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository
}