package com.vladiyak.sevenwindsstudiotask.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationItem
import com.vladiyak.sevenwindsstudiotask.data.models.location.Point

@Entity("coffee_shop")
data class CoffeeShopEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    @Embedded
    val point: Point
)

fun CoffeeShopEntity.toModel() = LocationItem(id, name, point, 0.0)
fun LocationItem.toEntity() = CoffeeShopEntity(id, name, point)

fun List<CoffeeShopEntity>.toModel() = map(CoffeeShopEntity::toModel)
fun List<LocationItem>.toEntity() = map(LocationItem::toEntity)
