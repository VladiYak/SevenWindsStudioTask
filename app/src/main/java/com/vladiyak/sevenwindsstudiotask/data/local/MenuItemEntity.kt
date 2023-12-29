package com.vladiyak.sevenwindsstudiotask.data.local

import android.view.MenuItem
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem

@Entity("menu_item")
data class MenuItemEntity(
    @PrimaryKey
    val id: Int,
    val coffeeShopId: Int,
    val name: String,
    val imageUrl: String,
    val price: Int
)

fun MenuItemEntity.toModel() = CoffeeItem(id, name, imageUrl, price)
fun CoffeeItem.toEntity(coffeeShopId: Int) = MenuItemEntity(id, coffeeShopId, name, imageURL, price)

fun List<MenuItemEntity>.toModel() = map(MenuItemEntity::toModel)
fun List<CoffeeItem>.toEntity(coffeeShopId: Int) = map { it.toEntity(coffeeShopId) }