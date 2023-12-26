package com.vladiyak.sevenwindsstudiotask.data.models.menu

data class CoffeeItem(
    val id: Int,
    val imageURL: String,
    val name: String,
    val price: Int,
    var quantity: Int = 0
)