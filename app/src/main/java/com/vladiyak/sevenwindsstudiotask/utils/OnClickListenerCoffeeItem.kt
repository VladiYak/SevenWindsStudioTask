package com.vladiyak.sevenwindsstudiotask.utils

import com.vladiyak.sevenwindsstudiotask.data.models.location.LocationItem
import com.vladiyak.sevenwindsstudiotask.data.models.menu.CoffeeItem

interface OnClickListenerCoffeeItem {
    fun onItemClick(coffeeItem: CoffeeItem)
}