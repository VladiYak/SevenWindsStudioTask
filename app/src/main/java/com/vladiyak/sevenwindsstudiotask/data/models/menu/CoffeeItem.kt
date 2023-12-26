package com.vladiyak.sevenwindsstudiotask.data.models.menu

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoffeeItem(
    val id: Int,
    val imageURL: String,
    val name: String,
    val price: Int,
    var quantity: Int = 0
) : Parcelable