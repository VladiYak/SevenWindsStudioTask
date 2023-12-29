package com.vladiyak.sevenwindsstudiotask.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("cart_item")
data class CartItemEntity(
    @PrimaryKey
    val menuItemId: Int,
    val count: Int
)
