package com.vladiyak.sevenwindsstudiotask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CoffeeShopEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun coffeeShopsDao(): CoffeeShopsDao
}