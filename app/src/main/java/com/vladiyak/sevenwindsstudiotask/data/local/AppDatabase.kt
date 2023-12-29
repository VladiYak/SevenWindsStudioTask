package com.vladiyak.sevenwindsstudiotask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vladiyak.sevenwindsstudiotask.data.local.dao.CartDao
import com.vladiyak.sevenwindsstudiotask.data.local.dao.CoffeeShopsDao
import com.vladiyak.sevenwindsstudiotask.data.local.dao.MenuDao
import com.vladiyak.sevenwindsstudiotask.data.local.entity.CartItemEntity
import com.vladiyak.sevenwindsstudiotask.data.local.entity.CoffeeShopEntity

@Database(
    entities = [
        CoffeeShopEntity::class,
        MenuItemEntity::class,
        CartItemEntity::class
    ], version = 3, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun coffeeShopsDao(): CoffeeShopsDao
    abstract fun cartDao(): CartDao
    abstract fun menuDao(): MenuDao

}