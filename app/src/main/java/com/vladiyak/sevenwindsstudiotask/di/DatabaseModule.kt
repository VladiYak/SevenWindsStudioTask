package com.vladiyak.sevenwindsstudiotask.di

import android.content.Context
import androidx.room.Room
import com.vladiyak.sevenwindsstudiotask.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "database")
            .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideCoffeeShopsDao(appDatabase: AppDatabase) = appDatabase.coffeeShopsDao()

}